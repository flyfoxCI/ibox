package cn.ibox.cfg;

import ibox.util.properties.PropertiesContent;
import cn.ibox.interceptor.LoginInterceptor;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.SimpleNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;


public class AppConfig extends JFinalConfig {
    private  Routes routes;
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setErrorView(401, "/common/401.html");//没有经过验证
        me.setErrorView(403, "/common/403.html");//如果没有权限访问对应的资源，返回HTTP状态码403
        me.setError404View("/common/404.html");
        me.setError500View("/common/500.html");
    }
    public void configRoute(Routes me) {
        setRoutes(me);
        me.add(new AutoBindRoutes());
    }
    public void configPlugin(Plugins me) {
    	DruidPlugin druidPlugin=new DruidPlugin(PropertiesContent.get("jdbc.url"),
    							PropertiesContent.get("jdbc.username"), PropertiesContent.get("jdbc.password"),PropertiesContent.get("jdbc.driverClassName"));
		me.add(druidPlugin);
    	AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(druidPlugin, SimpleNameStyles.LOWER_UNDERLINE);
		autoTableBindPlugin.setShowSql(Boolean.parseBoolean(PropertiesContent.get("showSql")));
		String db = PropertiesContent.get("jdbc.dbType");
		if ("mysql".equals(db)) {
			autoTableBindPlugin.setDialect(new MysqlDialect());
		} else if ("oracle".equals(db)) {
			autoTableBindPlugin.setDialect(new OracleDialect());
		} else {
			autoTableBindPlugin.setDialect(new AnsiSqlDialect());
		}
		me.add(autoTableBindPlugin);
		ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
		me.add(shiroPlugin);
    	
    	
    }
    public void configInterceptor(Interceptors me) {
    	me.add(new LoginInterceptor());
    	me.add(new SessionInViewInterceptor());
    	me.add(new ShiroInterceptor());
    }
    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("ctx"));
        


    }
	public Routes getRoutes() {
		return routes;
	}
	public void setRoutes(Routes routes) {
		this.routes = routes;
	}
	
}