package com.greenpineyu.fel.function.operator;

import static com.greenpineyu.fel.common.NumberUtil.toDouble;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.common.NumberUtil;
import com.greenpineyu.fel.common.ObjectUtils;
import com.greenpineyu.fel.common.ReflectUtil;
import com.greenpineyu.fel.compile.FelMethod;
import com.greenpineyu.fel.compile.SourceBuilder;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.StableFunction;
import com.greenpineyu.fel.parser.FelNode;

public class Add extends StableFunction  {


	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


	/*
	 * (non-Javadoc)
	 * 
	 * @see .script.function.Function#call(.script.AstNode,
	 * .script.context.ScriptContext)
	 */
	
	public Object call(FelNode node, FelContext context) {
		Object[] children = evalArgs(node, context);
		return call(children);
	}
	
	public static Object[] evalArgs(FelNode node, FelContext context) {
		Object[] returnMe = null;
		List<FelNode> children = node.getChildren();
		if(children!=null&& children.size()>0){
			Object[] args = children.toArray();
			int size = args.length;
			returnMe = new Object[size];
			System.arraycopy(args, 0, returnMe, 0, size);
			for (int i = 0; i < size; i++) {
				Object child = args[i];
				if (child instanceof Expression) {
					Expression childExp = ((Expression) child);
					returnMe[i] = childExp.eval(context);
				}
			}
		}
		return returnMe;
	}
	
	public Object call(Object[] arguments) {
		Object args0 = arguments[0];
		Object args1 = arguments[1];
		
		if( args0 instanceof List || args1 instanceof List ) {
			if( args0 instanceof List && !(args1 instanceof List)  ) {
				List list0 = (List)args0;
				return (Integer)(list0.get(0)) + (Integer)args1;
			}
			if( !(args0 instanceof List) && (args1 instanceof List)  ) {
				List list1 = (List)args1;
				return (Integer)(list1.get(0)) + (Integer)args0;
			}
			
			List<Integer> rt = new ArrayList<>();
			List list0 = (List)args0;
			List list1 = (List)args1;
			for( int i = 0;i<list0.size()&&i<list1.size();i++ ) {
				rt.add( (Integer)list0.get(i) + (Integer)list1.get(i) );
			}
			return rt;
		}else {
			return (Integer)args0 + (Integer)args1;
		}
	}

	
	public String getName() {
		return "+";
	}

	
	public FelMethod toMethod(FelNode node, FelContext ctx) {
		Class<?> type = null;
		List<FelNode> children = node.getChildren();
		StringBuilder sb = new StringBuilder();
		if (children.size() == 2) {
			FelNode left = children.get(0);
			SourceBuilder lm = left.toMethod(ctx);
			appendArg(sb, lm,ctx,left);
			Class<?> leftType = lm.returnType(ctx, left);
			
			FelNode right = children.get(1);
			sb.append("+");
			SourceBuilder rm = right.toMethod(ctx);
			Class<?> rightType = rm.returnType(ctx, right);
			if(CharSequence.class.isAssignableFrom(leftType)){
				type = leftType;
			} else if (CharSequence.class.isAssignableFrom(rightType)) {
				type = rightType;
			}else if(ReflectUtil.isPrimitiveOrWrapNumber(leftType)
					&&ReflectUtil.isPrimitiveOrWrapNumber(rightType)){
				type = NumberUtil.arithmeticClass(leftType, rightType);
			}else {
				//涓嶆敮鎸佺殑绫诲瀷锛岃繑鍥炲瓧绗︿覆銆�
				type = String.class;
			}
			appendArg(sb, rm,ctx,right);
			
		} else if (children.size() == 1) {
			FelNode right = children.get(0);
			SourceBuilder rm = right.toMethod(ctx);
			Class<?> rightType = rm.returnType(ctx, right);
			if(ReflectUtil.isPrimitiveOrWrapNumber(rightType)){
				appendArg(sb, rm,ctx,right);
			}
			type = rightType;
		}
		
//		appendArg(sb, rm,ctx,right);
		FelMethod m = new FelMethod(type, sb.toString());
		return m;
	}
	

	private void appendArg(StringBuilder sb, SourceBuilder argMethod,FelContext ctx,FelNode node) {
		Class<?> t = argMethod.returnType(ctx, node);
		sb.append("(");
		if (ReflectUtil.isPrimitiveOrWrapNumber(t)
				|| CharSequence.class.isAssignableFrom(t)) {
			// 鏁板�煎瀷鍜屽瓧绗﹀瀷鏃讹紝鐩存帴娣诲姞
			sb.append(argMethod.source(ctx, node));
		} else {
			sb.append("ObjectUtils.toString(").append(argMethod.source(ctx, node))
					.append(")");
		}
		sb.append(")");
	}

	
}
