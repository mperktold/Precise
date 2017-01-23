package it.unibz.precise.model;

import java.util.Map;

/**
 * Represents an exception for an invalid location because the indicated path does not exist in the
 * hierarchy.
 * 
 * @author MatthiasP
 *
 */
public class NonExistingLocationException extends InvalidLocationException {

	private static final long serialVersionUID = 1L;

	public NonExistingLocationException(Task task, Map<String, PatternEntry> pattern,
		Map<String, PatternEntry> parentPattern, Attribute attr, String value)
	{
		this(task, pattern, parentPattern, attr.getName(), value);
	}

	public NonExistingLocationException(Task task, Map<String, PatternEntry> pattern,
		Map<String, PatternEntry> parentPattern, String attrName, String value)
	{
		super(
			task, pattern,
			"There is no " + attrName + "=" + value + " in location " + PatternEntry.toKeyValueString(parentPattern)
		);
	}

}
