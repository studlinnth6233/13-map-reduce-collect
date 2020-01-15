package de.thro.inf.prg3.a13.utils;

import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for printing collections
 *
 * @author Peter Kurfer
 */
public abstract class PrintUtils
{
	private PrintUtils()
	{

	}

	/**
	 * Convert Map to String
	 *
	 * @param mapToPrint map that should be converted
	 * @param <K>        type of Map key
	 * @param <V>        type of Map value
	 *
	 * @return converted String
	 */
	public static <K, V> String mapToString(Map<K, V> mapToPrint)
	{
		return mapToString(mapToPrint, Object::toString);
	}

	/**
	 * Convert Map to String
	 *
	 * @param mapToPrint   map that should be converted
	 * @param keyFormatter function to format the key of the map entry
	 * @param <K>          type of Map key
	 * @param <V>          type of Map value
	 *
	 * @return converted String
	 */
	public static <K, V> String mapToString(Map<K, V> mapToPrint, Function<K, String> keyFormatter)
	{
		return mapToString(mapToPrint, keyFormatter, Object::toString);
	}

	/**
	 * Convert Map to String
	 *
	 * @param mapToPrint     map that should be converted
	 * @param keyFormatter   function to format the key of the map entry
	 * @param valueFormatter function to format value of the map entry
	 * @param <K>            type of Map key
	 * @param <V>            type of Map value
	 *
	 * @return converted String
	 */
	public static <K, V> String mapToString(Map<K, V> mapToPrint, Function<K, String> keyFormatter, Function<V, String> valueFormatter)
	{
		requireNonNull(mapToPrint);
		requireNonNull(keyFormatter);
		requireNonNull(valueFormatter);

		StringBuilder builder = new StringBuilder();
		builder.append("[\n");

		for (Map.Entry<K, V> entry : mapToPrint.entrySet())
		{
			builder.append("\t{ ")
				.append(keyFormatter.apply(entry.getKey()))
				.append(": ")
				.append(valueFormatter.apply(entry.getValue()))
				.append(" },\n");
		}

		builder.setLength(builder.length() - 2);
		builder.append("\n]");

		return builder.toString();
	}

	/**
	 * Convert iterable to String
	 *
	 * @param iterableToPrint iterable instance that should be converted
	 * @param <T>             type of list content
	 *
	 * @return converted String
	 */
	public static <T> String iterableToString(Iterable<T> iterableToPrint)
	{
		return iterableToString(iterableToPrint, Object::toString);
	}

	/**
	 * Convert iterable to String
	 *
	 * @param iterableToPrint iterable instance that should be converted
	 * @param valueFormatter  function to format list entry
	 * @param <T>             type of list content
	 *
	 * @return converted String
	 */
	public static <T> String iterableToString(Iterable<T> iterableToPrint, Function<T, String> valueFormatter)
	{
		requireNonNull(iterableToPrint);
		requireNonNull(valueFormatter);

		StringBuilder builder = new StringBuilder();
		builder.append("[\n");

		for (T value : iterableToPrint)
		{
			builder.append("\t")
				.append(valueFormatter.apply(value))
				.append(",\n");
		}

		builder.setLength(builder.length() - 2);
		builder.append("\n]");

		return builder.toString();
	}
}
