package ibox.util.authorized;

import java.util.List;

import ibox.util.constant.Constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ibox.model.Resc;
import cn.ibox.model.RoleResc;
import cn.ibox.model.RoleUser;
import cn.ibox.model.User;

public class MyShiroRealm extends AuthorizingRealm{
	private Logger log = LoggerFactory.getLogger(MyShiroRealm.class);
	public static final String authenticationQuery=Constant.DEFAULT_AUTHENTICATION_QUERY;
	protected String userRolesQuery = Constant.DEFAULT_USER_ROLES_QUERY;
	protected String permissionsQuery = Constant.DEFAULT_PERMISSIONS_QUERY;
	protected String adminPermissionsQuery = Constant.DEFAULT_ADMIN_PERMISSIONS_QUERY;
	protected boolean permissionsLookupEnabled = Constant.PERMISSIONS_LOOKUP_ENABLED;
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		// Null username is invalid
		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		try {
			User loginUser = User.dao.findFirst(authenticationQuery, username);
			String password = loginUser.getStr(User.PASSWORD);
			if (password != null) {
				Subject subject = SecurityUtils.getSubject();
				subject.getSession().setAttribute(Constant.SHIRO_LOGIN_USER, loginUser);
				return new SimpleAuthenticationInfo(loginUser, password.toCharArray(), getName());
			} else {
				throw new UnknownAccountException("No account found for user [" + username + "]");
			}
		} catch (Exception e) {
			final String message = "There was a SQL error while authenticating user [" + username + "]";
			if (log.isErrorEnabled()) {
				log.error(message, e);
			}
			// Rethrow any SQL errors as an authentication exception
			throw new AuthenticationException(message, e);
		}
	}

	/**
	 * 此方法是为用户加Role、permission的 This implementation of the interface expects the principals
	 * collection to return a String username keyed off of this realm's {@link #getName() name}
	 * 
	 * @see #getAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// null usernames are invalid
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		User loginUser = (User) getAvailablePrincipal(principals);
		// User loginUser = (User) principals.fromRealm(getName()).iterator().next();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			// Retrieve roles and permissions from database
			List<RoleUser> roleUsers = RoleUser.dao.find(userRolesQuery, loginUser.getInt(User.USER_ID));
			if (permissionsLookupEnabled) {
				getPermissions(info, loginUser.getStr(User.USERNAME), roleUsers);
			}

		} catch (Exception e) {
			final String message = "There was a SQL error while authorizing user [" + loginUser.getStr(User.USERNAME) + "]";
			if (log.isErrorEnabled()) {
				log.error(message, e);
			}
			// Rethrow any SQL errors as an authorization exception
			throw new AuthorizationException(message, e);
		}

		return info;
	}

	protected void getPermissions(SimpleAuthorizationInfo info, String username, List<RoleUser> roleUsers) {	
		for (RoleUser roleUser : roleUsers) {
			if (!username.equals(Constant.SYSTEM_ADMINISTRATOR)) {
				int role_id=roleUser.getInt(RoleUser.ROLE_ID);
				List<RoleResc> roleRescs = RoleResc.dao.find(permissionsQuery,role_id);
				for (RoleResc roleResc : roleRescs) {
					String rescId=String.valueOf(roleResc.getInt(RoleResc.RESC_ID));
					info.addStringPermission(rescId);
					 
				}
			
				
			} else {
				List<Resc> rescsList = Resc.dao.find(adminPermissionsQuery); // 管理员默认获取全部权限
				for (Resc resc : rescsList) {
					info.addStringPermission(String.valueOf(resc.getInt(Resc.ID)));
				}
			
				
			}
			
			info.addRole(String.valueOf(roleUser.getInt(RoleUser.ROLE_ID)));
		}
	}

	
	

}
