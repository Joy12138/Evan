package com.warm.util.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class IntHashMap<T> {
	// 初始数组长度(必须是2的N次,这样在计算索引的时候，可以利用的空间和碰撞几率都更好)
	private static final int DEFAULT_INITAL_CAPACITY = 32;
	// 最大容量
	private static final int MAXIMUM_CAPACITY = 1073741824;
	// 系统默认负载因子
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	// Entry数组,不可以被序列化
	private transient Entry[] table;
	private transient int size;
	// 容量+装载因子的极限值
	private int threshold;
	private final float loadFactor;
	// 用于确保使用迭代器，HashMap并未进行更改
	private transient volatile int modCount;

	public IntHashMap() {
		this(32, 0.75f);
	}

	public IntHashMap(int initialCapacity) {
		this(initialCapacity, 0.75f);
	}

	public IntHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("illegal Capacity :" + initialCapacity);
		if (loadFactor <= 0.0F)
			throw new IllegalArgumentException("Illegal Load: " + loadFactor);
		if (initialCapacity == 0)
			initialCapacity = 1;
		if (initialCapacity > 1073741824)
			initialCapacity = 1073741824;
		this.loadFactor = loadFactor;
		this.table = new Entry[initialCapacity];
		this.threshold = (int) (initialCapacity * loadFactor);
	}

	public static class Entry<T> {
		// 这里直接使用int，不会自动封包创建对象
		final int key;
		T value;
		Entry<T> next;

		Entry(int key, T value, Entry<T> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public int getKey() {
			return this.key;
		}

		public T getValue() {
			return (T) this.value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public boolean contains(Object value) {
		if (value == null) {
			return containsNullValue();
		}
		Entry<T>[] tab = this.table;
		for (int i = tab.length; i-- > 0;) {
			for (Entry<T> e = tab[i]; e != null; e = e.next) {
				if (e.value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean containsNullValue() {
		Entry<T>[] tab = this.table;
		for (int i = 0; i < tab.length; i++) {
			for (Entry<T> e = tab[i]; e != null; e = e.next) {
				if (e.value == null) {
					return true;
				}
			}
		}
		return false;
	}

	final Entry<T> getEntry(int key) {
		for (Entry<T> e = this.table[indexFor(key, this.table.length)]; e != null; e = e.next) {
			if (e.key == key)
				return e;
		}
		return null;
	}

	public boolean containsKey(int key) {
		return getEntry(key) != null;
	}

	private int indexFor(int h, int length) {
		// 这里就是length尽量为2的N次方的原因
		return h & (length - 1);
	}

	//获取的是一个entry数组
	public T get(int key) {
		Entry<T>[] tab = this.table;
		int index = indexFor(key, this.table.length);
		for (Entry<T> e = tab[index]; e != null; e = e.next) {
			if (e.key == key) {
				return (T) e.value;
			}
		}
		return null;
	}

	public T put(int key, T value) {
		Entry<T>[] tab = this.table;
		int index = indexFor(key, this.table.length);
		for (Entry<T> e = tab[index]; e != null; e = e.next) {
			if (e.key == key) {
				T old = e.value;
				e.value = value;
				return old;
			}
		}
		this.modCount += 1;
		addEntry(key, value, index);
		return null;
	}

	void addEntry(int key, T value, int bucketIndex) {
		Entry<T> e = this.table[bucketIndex];
		this.table[bucketIndex] = new Entry<T>(key, value, e);
		if (++this.size > this.threshold) {
			resize(2 * this.table.length);
		}
	}

	void resize(int newCapacity) {
		Entry<T>[] oldTable = this.table;
		int oldCapacity = oldTable.length;
		if (oldCapacity == 1073741824) {
			this.threshold = Integer.MAX_VALUE;
			return;
		}
		Entry<T>[] newTable = new Entry[newCapacity];
		transfer(newTable);
		this.table = newTable;
		this.threshold = ((int) (newCapacity * this.loadFactor));
	}

	void transfer(Entry<T>[] newTable) {
		Entry<T>[] src = this.table;
		int newCapacity = newTable.length;
		for (int j = 0; j < src.length; j++) {
			Entry<T> e = src[j];
			if (e != null) {
				src[j] = null;
				do {
					Entry<T> next = e.next;
					int i = indexFor(e.key, newCapacity);
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
				} while (e != null);
			}
		}
	}

	public boolean containsValue(Object value) {
		return contains(value);
	}

	public T remove(int key) {
		Entry<T>[] tab = this.table;
		int index = indexFor(key, this.table.length);
		Entry<T> e = tab[index];
		for (Entry<T> prev = null; e != null; e = e.next) {
			if (e.key == key) {
				this.modCount += 1;
				if (prev != null) {
					prev.next = e.next;
				} else {
					tab[index] = e.next;
				}
				this.size -= 1;
				T oldValue = e.value;
				e.value = null;
				return oldValue;
			} else
				prev = e;
		}
		return null;
	}

	public void clear() {
		this.modCount += 1;
		Entry<T>[] tab = this.table;
		int index = tab.length;
		for (;;) {
			index--;
			if (index < 0) {
				break;
			}
			tab[index] = null;
		}
		this.size = 0;
	}

	volatile transient Collection<T> values = null;

	public Collection<T> values() {
		Collection<T> vs = this.values;
		return vs != null ? vs : (this.values = new Values(null));
	}

	private final class Values extends AbstractCollection<T> {
		private Values(Object object) {
		}

		public Iterator<T> iterator() {
			return new IntHashMap.ValueIterator(IntHashMap.this, null);
		}

		public int size() {
			return IntHashMap.this.size;
		}

		public boolean contains(Object o) {
			return IntHashMap.this.containsValue(o);
		}

		public void clear() {
			IntHashMap.this.clear();
		}
	}

	private final class ValueIterator extends IntHashMap<T>.IntHashIterator<T> {
		private ValueIterator() {
			super();
		}
		
		
		
		public ValueIterator(IntHashMap<T> intHashMap, Object object) {
			
		}



		public T next() {
			return (T) nextEntry().value;
		}
	}

	private abstract class IntHashIterator<V> implements Iterator<V> {
		IntHashMap.Entry<T> next;
		int expectedModCount = IntHashMap.this.modCount;
		int index;
		IntHashMap.Entry<T> current;

		IntHashIterator() {
			IntHashMap.Entry<T>[] t;
			if (IntHashMap.this.size > 0) {
				t = IntHashMap.this.table;
				while ((this.index < t.length) && ((this.next = t[(this.index++)]) == null)) {
				}
			}
		}

		public final boolean hasNext() {
			return this.next != null;
		}

		final IntHashMap.Entry<T> nextEntry() {
			if (IntHashMap.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			IntHashMap.Entry<T> e = this.next;
			if (e == null) {
				throw new NoSuchElementException();
			}
			if ((this.next = e.next) == null) {
				IntHashMap.Entry<T>[] t = IntHashMap.this.table;
				while ((this.index < t.length) && ((this.next = t[(this.index++)]) == null)) {
				}
			}
			this.current = e;
			return e;
		}

		public void remove() {
			if (this.current == null) {
				throw new IllegalStateException();
			}
			if (IntHashMap.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			int k = this.current.key;
			this.current = null;
			IntHashMap.this.remove(k);
			this.expectedModCount = IntHashMap.this.modCount;
		}
	}

	private transient Set<Entry<T>> entrySet = null;

	public Set<Entry<T>> entrySet() {
		Set<Entry<T>> es = this.entrySet;
		return es != null ? es : (this.entrySet = new EntrySet(null));
	}

	private final class EntrySet extends AbstractSet<IntHashMap.Entry<T>> {
		private EntrySet(Object object) {
		}

		public Iterator<IntHashMap.Entry<T>> iterator() {
			return new IntHashMap.EntryIterator(IntHashMap.this, null);
		}

		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry)) {
				return false;
			}
			IntHashMap.Entry<T> e = (IntHashMap.Entry) o;
			IntHashMap.Entry<T> candidate = IntHashMap.this.getEntry(e.getKey());
			return (candidate != null) && (candidate.equals(e));
		}

		public boolean remove(Object o) {
			if (!(o instanceof IntHashMap.Entry)) {
				return false;
			}
			return IntHashMap.this.remove(((IntHashMap.Entry) o).key) != null;
		}

		public int size() {
			return IntHashMap.this.size;
		}

		public void clear() {
			IntHashMap.this.clear();
		}
	}

	private final class EntryIterator extends IntHashMap<T>.IntHashIterator<IntHashMap.Entry<T>> {
		private EntryIterator(IntHashMap<T> intHashMap, Object object) {
			super();
		}

		public IntHashMap.Entry<T> next() {
			return nextEntry();
		}
	}
}
