/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openzen.zencode.parser.statement;

import org.openzen.zencode.symbolic.scope.IMethodScope;
import org.openzen.zencode.symbolic.statement.Statement;
import org.openzen.zencode.symbolic.statement.StatementBreak;
import org.openzen.zencode.symbolic.statement.StatementNull;
import org.openzen.zencode.symbolic.statement.StatementSwitch;
import org.openzen.zencode.lexer.Token;
import org.openzen.zencode.lexer.ZenLexer;
import static org.openzen.zencode.lexer.ZenLexer.*;
import org.openzen.zencode.symbolic.expression.IPartialExpression;
import org.openzen.zencode.util.CodePosition;

/**
 *
 * @author Stan
 */
public class ParsedStatementBreak extends ParsedStatement
{
	public static ParsedStatementBreak parse(ZenLexer lexer)
	{
		CodePosition position = lexer.required(T_BREAK, "break expected").getPosition();

		Token label = lexer.optional(TOKEN_ID);
		lexer.required(T_SEMICOLON, "; expected");

		return new ParsedStatementBreak(position, label == null ? null : label.getValue());
	}

	private final String label;

	public ParsedStatementBreak(CodePosition position, String label)
	{
		super(position);

		this.label = label;
	}

	@Override
	public <E extends IPartialExpression<E>> Statement<E> compile(IMethodScope<E> scope)
	{
		Statement<E> controlStatement = scope.getControlStatement(label);

		if (controlStatement == null) {
			if (label == null)
				scope.getErrorLogger().errorNoBreakableControlStatement(getPosition());
			else
				scope.getErrorLogger().errorNoLabeledControlStatement(getPosition(), label);
			
			return new StatementNull<E>(getPosition(), scope);
		} else
			return new StatementBreak<E>(getPosition(), scope, controlStatement);
	}

	@Override
	public <E extends IPartialExpression<E>>
		 void compileSwitch(IMethodScope<E> scope, StatementSwitch<E> forSwitch)
	{
		forSwitch.onStatement(compile(scope));
	}
}
