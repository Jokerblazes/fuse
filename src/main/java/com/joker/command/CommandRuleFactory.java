package com.joker.command;

import com.joker.command.rule.CommandRule;
import com.joker.entity.CommandKey;

public class CommandRuleFactory {
	public static CommandRule createCommandRule(CommandKey key) throws InstantiationException, IllegalAccessException {
		return (CommandRule) key.getRule_class().newInstance();
	}
}
