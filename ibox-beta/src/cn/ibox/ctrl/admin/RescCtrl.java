package cn.ibox.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.Resc;
import cn.ibox.model.RescTreeNode;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
@ControllerBind(controllerKey="/admin/resc")
public class RescCtrl extends BaseController<Resc>{
	public void index(){
		
	}
	public void getRescList(){
		 int parentId=getParaToInt("id");
		 List<Resc> rescsList=Resc.dao.findChildren(parentId);
		 List<RescTreeNode> treeList=new ArrayList<RescTreeNode>();
		for(int i=0;i<rescsList.size();i++){
			RescTreeNode node=new RescTreeNode();
			node.setIsParent(true);
			node.setId(rescsList.get(i).getInt("id"));
			node.setName(rescsList.get(i).getStr("name"));
			treeList.add(node);
		}
		Controller c=this.keepPara();
		c.renderJson(treeList);
		 
	}

}
