/**
 *
 * <p>Title:Operator </p>
 * <p>Description:表达式计算的运算符号 </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author 墙辉
 * @version 1.0
 */

package com.ql.util.express.instruction.op;

import java.util.List;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.opdata.OperateDataAttr;

/**
 * 操作符号定义
 * 
 * @author qhlhl2010@gmail.com
 * 
 */

public abstract class OperatorBase implements java.io.Serializable {
	
	protected String aliasName;

	protected String name;

	protected String errorInfo;
	/**
	 * 是否需要高精度计算
	 */
	protected boolean isPrecise = false;
	/**
	 * 操作数描述
	 */
	protected String[] operDataDesc;
	/**
	 * 操作数的其它定义
	 */
	protected String[] operDataAnnotation;
	public Object[] toObjectList(InstructionSetContext  parent, OperateData[] list)
			throws Exception {
		if (list == null) {
			return new Object[0];
		}
		Object[] result = new Object[list.length];
		for (int i = 0; i < list.length; i++) {
			if(list[i] instanceof OperateDataAttr){
				result[i] = ((OperateDataAttr) list[i]).getName()+":"+list[i].getObject(parent);
			}else{
				result[i] = list[i].getObject(parent);
			}
		}
		return result;
	}	
	public OperateData execute(InstructionSetContext  context,
			OperateData[] list, List<String> errorList) throws Exception {
		OperateData result = null;
		result = this.executeInner(context, list);
		//输出错误信息
		if (errorList != null && this.errorInfo != null && result != null) {
			Object obj = result.getObject(context);
			if (    obj != null
					&& obj instanceof Boolean
					&& ((Boolean) obj).booleanValue() == false) {
				String tmpStr = ExpressUtil.replaceString(this.errorInfo,
						toObjectList(context, list));
				if(errorList.contains(tmpStr) == false){
				    errorList.add(tmpStr);
				}
			}
		}
		return result;
	}
    public String toString(){
    	if(this.aliasName != null){
    		return this.aliasName;
    	}else{
    		return this.name;
    	}
    }
	public abstract OperateData executeInner(InstructionSetContext  parent, OperateData[] list) throws Exception;

	public String[] getOperDataDesc(){
		return this.operDataDesc;
	}
	public String[] getOperDataAnnotaion(){
		return this.operDataAnnotation;
	}
	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public String getAliasName() {
		if(this.aliasName != null){
			return this.aliasName;
		}else{
			return this.name;
		}
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public boolean isPrecise() {
		return isPrecise;
	}
	public void setPrecise(boolean isPrecise) {
		this.isPrecise = isPrecise;
	}
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}	
}

class OperatorFunction extends OperatorBase {
	public OperatorFunction(String aName) {
		this.name = aName;
	}
	public OperatorFunction(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}

	public OperateData executeInner(InstructionSetContext context, OperateData[] list) throws Exception {
		throw new Exception("还没有实现");
	}
}

class OperatorReturn extends OperatorBase{
	public OperatorReturn(String name) {
		this.name = name;
	}
	public OperatorReturn(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		return executeInner(parent);
	}

	public OperateData executeInner(InstructionSetContext parent) throws Exception {
		throw new Exception("return 是通过特殊指令来实现的，不能支持此方法");
	}	
}
class OperatorCall extends OperatorBase{
	public OperatorCall(String name) {
		this.name = name;
	}
	public OperatorCall(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		throw new Exception("call 是通过特殊指令来实现的，不能支持此方法");
	}	
}

class OperatorBreak extends OperatorBase{
	public OperatorBreak(String name) {
		this.name = name;
	}
	public OperatorBreak(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		throw new Exception("OperatorBreak 是通过特殊指令来实现的，不能支持此方法");
	}	
}
class OperatorContinue extends OperatorBase{
	public OperatorContinue(String name) {
		this.name = name;
	}
	public OperatorContinue(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		throw new Exception("OperatorContinue 是通过特殊指令来实现的，不能支持此方法");
	}	
}

class OperatorFor extends OperatorBase {
	public OperatorFor(String aName) {
		this.name = aName;
	}

	public OperatorFor(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	
	public  OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		throw new Exception("cache 是通过特殊指令来实现的，不能支持此方法");
	}

}
