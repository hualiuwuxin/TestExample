package com.greenpineyu.fel.function.operator;

import java.util.List;

import com.greenpineyu.fel.common.Null;
import com.greenpineyu.fel.common.ReflectUtil;
import com.greenpineyu.fel.compile.FelMethod;
import com.greenpineyu.fel.compile.SourceBuilder;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.StableFunction;
import com.greenpineyu.fel.function.TolerantFunction;
import com.greenpineyu.fel.parser.FelNode;

// 二次开发等于逻辑(强化数字型字符串比较逻辑) 
public class Equal extends StableFunction {

	public String getName() {
		return "==";
	}

	public Object call(FelNode node, FelContext context) {
		List<FelNode> children = node.getChildren();
		if (children != null && children.size() == 2) {
			Object left = TolerantFunction.eval(context, children.get(0));
			Object right = TolerantFunction.eval(context, children.get(1));
			return Boolean.valueOf(compare(left, right));
		}
		throw new NullPointerException("传入参数数组为空或者参数个数不正确!");
	}

	boolean compare(Object left, Object right) {
		return equals(left, right);
	}

	public static boolean equals(Object left, Object right) {
		return true;
	}
	
	public FelMethod toMethod(FelNode node, FelContext ctx) {
		String operator = this.getName();
		return toMethod(node, ctx, operator);
	}

	private FelMethod toMethod(FelNode node, FelContext ctx, String operator) {
		StringBuilder sb = buildRelationExpr(node, ctx, operator);
		if(sb.length()==0){
			String left = getChildCode(node, ctx,0);
			String right = getChildCode(node, ctx, 1);
			String toMethod = toMethod(left, right);
			sb.append(toMethod);
		}
		return new FelMethod(Boolean.class, sb.toString());
	}

	String toMethod(String left, String right) {
		return "ObjectUtils.equals(" + left + "," + right + ")";
	}

	public static String getChildCode(FelNode node, FelContext ctx,int index) {
		List<FelNode> children = node.getChildren();
		FelNode child = children.get(index);
		SourceBuilder leftM = child.toMethod(ctx);
		String code = "(" + leftM.source(ctx, child) + ")";
		return code;
	}

	public static StringBuilder buildRelationExpr(FelNode node, FelContext ctx,
			String operator) {
		List<FelNode> child = node.getChildren();
		FelNode leftNode = child.get(0);
		FelNode rightNode = child.get(1);
		SourceBuilder leftM = leftNode.toMethod(ctx);
		SourceBuilder rightM = rightNode.toMethod(ctx);
		Class<?> leftType = leftM.returnType(ctx, leftNode);
		Class<?> rightType = rightM.returnType(ctx, rightNode);
		String left = "(" + leftM.source(ctx, leftNode) + ")";
		String right = "(" +rightM.source(ctx, rightNode) + ")";

		StringBuilder sb = new StringBuilder();
		// 只要有一个是数值型，就将另一个也转成值型。
		if(ReflectUtil.isPrimitiveNumber(leftType)&&ReflectUtil.isPrimitiveNumber(leftType)){
			//如果左右都是基本数值类型，直接==运算就行了。
			sb.append(left).append(operator).append(right);
		}else	if (Number.class.isAssignableFrom(leftType)) {
			sb.append(left);
			sb.append(operator);
			appendNumber(rightType, right, sb);
		} else if (Number.class.isAssignableFrom(rightType)) {
			appendNumber(leftType, left, sb);
			sb.append(operator);
			sb.append(right);
		} else if (Boolean.class.isAssignableFrom(leftType)) {
			sb.append(left);
			sb.append(operator);
			appendBoolean(rightType, right, sb);
		} else if (Boolean.class.isAssignableFrom(rightType)) {
			appendBoolean(leftType, left, sb);
			sb.append(operator);
			sb.append(right);
		} else if (String.class.isAssignableFrom(leftType)) {
			sb.append("StringUtils.equals(" + left + ",ObjectUtils.toString("
					+ right + "))");
		} else if (String.class.isAssignableFrom(rightType)) {
			sb.append("StringUtils.equals(ObjectUtils.toString(" + right + "),"
					+ left + ")");
		}
		return sb;
	}

	static public void appendNumber(Class<?> type, String src, StringBuilder sb) {
		if (Number.class.isAssignableFrom(type)) {
			// 当type是数值型时，使用toString
			sb.append(src);
		} else if (String.class.isAssignableFrom(type)) {
			// 当type是字符型时，转成double型
			sb.append("NumberUtil.toDouble(" + src + ")");
		} else if(Null.class.isAssignableFrom(type)){
			sb.append("Double.MIN_VALUE");
		}else{
			// 当type是Object时，this.equals
			// FIXME
		}
	}

	static public void appendBoolean(Class<?> type, String src, StringBuilder sb) {
		if (Boolean.class.isAssignableFrom(type)) {
			// 当type是Boolean时，使用toString
			sb.append(src);
		} else if (String.class.isAssignableFrom(type)) {
			// 当type是字符型时，转成double型
			sb.append("Boolean.valueOf(" + src + ")");
		} else {
			// 当type是Object时，this.equals
			// FIXME
		}
	}


}
