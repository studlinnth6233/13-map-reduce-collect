package de.thro.inf.prg3.a13.model;

import java.util.function.Supplier;

/**
 * Wrapper to store a lazy that is initialized on the first access and cached for further accesses
 *
 * @author Peter Kurfer
 */
public final class Lazy<T> {

	/**
	 * Supplier to initialize value on first access
	 */
	private Supplier<? extends T> lazySupplier;

	/**
	 * Cached value
	 */
	private T value;

	/**
	 * Default constructor
	 *
	 * @param lazySupplier supplier to initialize value when required
	 */
	private Lazy(final Supplier<? extends T> lazySupplier) {
		this.lazySupplier = lazySupplier;
	}

	/**
	 * Factory method for Lazy
	 *
	 * @param lazyValueSupplier supplier to initialize value when required
	 * @param <T>               type of the value to wrap
	 * @return new lazy instance
	 */
	public static <T> Lazy<T> lazyOf(final Supplier<? extends T> lazyValueSupplier) {
		return new Lazy<>(lazyValueSupplier);
	}

	/**
	 * Get value
	 *
	 * @return value resolved by the supplier or cached value
	 */
	public final synchronized T getValue() {
		if (value == null) {
			value = lazySupplier.get();
		}
		return value;
	}
}
