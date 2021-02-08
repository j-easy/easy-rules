/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.api;

import java.util.Objects;

/**
 * A class representing a named fact. Facts have unique names within a {@link Facts}
 * instance.
 * 
 * @param <T> type of the fact
 * @author Mahmoud Ben Hassine
 */
public class Fact<T> {
	
	private final String name;
	private final T value;

	/**
	 * Create a new fact.
	 * @param name of the fact
	 * @param value of the fact
	 */
	public Fact(String name, T value) {
		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(value, "value must not be null");
		this.name = name;
		this.value = value;
	}

	/**
	 * Get the fact name.
	 * @return fact name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the fact value.
	 * @return fact value
	 */
	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Fact{" +
				"name='" + name + '\'' +
				", value=" + value +
				'}';
	}

	/*
	 * The Facts API represents a namespace for facts where each fact has a unique name.
	 * Hence, equals/hashcode are deliberately calculated only on the fact name.
	 */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Fact<?> fact = (Fact<?>) o;
		return name.equals(fact.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
