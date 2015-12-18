package com.github.xzzpig.BukkitTools;

import java.util.*;
import java.util.regex.*;

public class TCalculate
{
	public static double getResult(String expression) {
		//String expression = "1+5*(2+1)";
		Pattern entryOfExpression = Pattern.compile("[0-9]+(\\.[0-9]+)?|\\(|\\)|\\+|-|\\*|/");
		Deque<Object> stack = new LinkedList<Object>();  //运算过程中要使用的栈
		List<Object> list = new LinkedList<Object>();  //运算过程中要使用的列表
		//用正则式分析表达式
		Matcher m = entryOfExpression.matcher(expression);
		//对分析出的每个语素处理
		while(m.find()) {
			String nodeString = expression.substring(m.start(), m.end());
			if(nodeString.matches("[0-9].*")) {
				list.add(Double.valueOf(nodeString));
			}else {
				//如果是运算符,根据不同情况处理
				OPNode opn = new OPNode(nodeString);
				int peekLevel = (stack.peek()==null)?0:((OPNode)stack.peek()).level;
				if(opn.level>peekLevel) {
					stack.push(opn);
				}else {
					if(opn.level==-1) {
						OPNode tempOpn = (OPNode)stack.pop();
						while(tempOpn.level!=-3) {
							list.add(tempOpn);  //出栈的运算符送列表
							tempOpn =(OPNode)stack.pop();
						}
					}else if(opn.level==-3) {
						stack.push(opn); //如果为"("直接入栈
					}else {
						//如果新运算符比栈顶运算符优先级低则一直出栈
						//直到栈空间或新运算符比栈顶运算符优先级高
						OPNode tempOpn = (OPNode)stack.pop();
						while(tempOpn.level>=opn.level) {
							list.add(tempOpn);
							if(stack.isEmpty()) break;
							tempOpn = (OPNode)stack.pop();
						}
						stack.push(opn); //新运算符号入栈
					}
				}
			}
		}
		OPNode tempOpn = null;
		//栈中剩余运算符送入列表
		while(!stack.isEmpty()) {
			tempOpn = (OPNode)stack.pop();
			list.add(tempOpn);
		}
		//后续表达示计算
		stack.clear();
		for(Object o:list) {
			if(o instanceof Double) {
				stack.push(o);
			}else {
				double opd2 = ((Double)stack.pop()).doubleValue();
				double opd1 = ((Double)stack.pop()).doubleValue();
				switch(((OPNode)o).op) {
					case '+':stack.push(opd1 + opd2);break;
					case '-':stack.push(opd1 - opd2);break;
					case '*':stack.push(opd1 * opd2);break;
					case '/':stack.push(opd1 / opd2);break;
				}
			}
		}
		return (double) stack.pop();
	}
}
class OPNode {
	char op;  //运算符符号
	int level; //运算符优先级
	
	public OPNode(String op) {
		this.op = op.charAt(0);
		//根据不同的运算符号分配优先级
		if(op.equals("+")||op.equals("-")) {
			this.level = 1; //加减运算优先级为1
		}else if(op.equals("*")||op.equals("/")) {
			this.level = 2; //乘除运算优先级为2
		}else if(op.equals("(")) {
			this.level = -3;  //"("优先级为-3
		}else {
			this.level = -1;   //")"优先级为-1
		}
	}
}
