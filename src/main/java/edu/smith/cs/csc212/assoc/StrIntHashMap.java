package edu.smith.cs.csc212.assoc;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.MapADT;
import me.jjfoley.adt.Pair;
import me.jjfoley.adt.errors.TODOErr;
import me.jjfoley.adt.impl.JavaList;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class StrIntHashMap extends MapADT<String, Integer> {
	/**
	 * This is our array of buckets; each cell is the "start" of a Singly-Linked-List!
	 */
	ArrayWrapper<EntryNode> buckets;

	/**
	 * Let's keep track of size separately so it can be O(1).
	 */
	int size;

	/**
	 * Let's keep track of numUsedBuckets so we know when to resize.
	 */
	int numUsedBuckets;

	public StrIntHashMap() {
		// We want all our buckets to start-off as null!
		this.buckets = new ArrayWrapper<>(7);
		// We start off with no elements!
		this.size = 0;
		// We start off with no used buckets!
		this.numUsedBuckets = 0;
	}

	/**
	 * Private helper method: into which bucket does this key belong?
	 * @param key - a string key.
	 * @return - the bucket index it would be in if we had it.
	 */
	private int whichBucket(String key) {
		return Math.abs(key.hashCode()) % this.buckets.size();
	}

	/**
	 * What percentage of the buckets in this HashMap are used?
	 * @return numUsedBuckets / numBuckets.
	 */
	private double loadFactor() {
		return this.numUsedBuckets / (double) this.buckets.size();
	}

	/**
	 * Resize method goals:
	 *  - create new array of buckets (bigger or smaller!)
	 *  - re-hash all elements into new array
	 *  - make sure size/numUsedBuckets are still consistent.
	 *
	 * @param newNumBuckets - tells us whether we're growing or shrinking.
	 */
	private void resize(int newNumBuckets) {
		ArrayWrapper<EntryNode> oldBuckets = this.buckets;
		this.buckets = new ArrayWrapper<>(newNumBuckets);

		this.numUsedBuckets = 0;

		// for all old buckets:
		for (int i=0; i<oldBuckets.size(); i++) {
			// for all entries in those buckets:
			for (EntryNode n = oldBuckets.getIndex(i); n != null; n = n.next) {
				// find-new-bucket:
				int newBucket = whichBucket(n.key);
				// add-to-front:
				EntryNode start = this.buckets.getIndex(newBucket);
				if (start == null) {
					this.numUsedBuckets++;
				}
				this.buckets.setIndex(newBucket, new EntryNode(n.key, n.value, start));
			}
		}

	}

	private void maybeShrink() {
		// Enforce a minimum bucket-size.
		if (this.buckets.size() > 7 && loadFactor() < 0.25) {
			// Shrink by roughly half.
			int newBuckets = this.buckets.size() / 2;
			// Try to keep the number of buckets odd:
			if (newBuckets % 2 == 0) {
				newBuckets -= 1;
			}
			this.resize(newBuckets);
		}
	}

	private void maybeGrow() {
		// 0. check whether we should resize or not:
		if (loadFactor() > 0.75) {
			this.resize(this.buckets.size() * 2 - 1);
		}
	}

	//// start:put
	@Override
	public void put(String key, @Nonnull Integer value) {
			// 1. Calculate which bucket contains our key.
			// 2. Get the list of entries in that bucket:
			// 3. Search the list for the pair we want:
			// 3.a. If found, update the node and leave this method early.
			// 3.b. If not found, add our key, value to the front of this list! O(1).
			// 4. Need to update our sizes manually! (and call maybeGrow)
			throw new TODOErr();

	}
	//// end

	public boolean contains(String key) {
		return this.get(key) != null;
	}

	//// start: get
	@Nullable
	public Integer get(String key) {
		// 1. Calculate which bucket contains our key.
		// 2. Get the list of entries in that bucket:
		// 3. Search the list for the pair we want:
		// 3.a. If we find it, return the value being stored!
		// 3.b. If we don't find it, return null!
		throw new TODOErr();
	}
	//// end

	public int size() {
		return this.size;
	}

	@Nullable
	public Integer remove(String key) {
		// 1. Calculate which bucket contains our key.
		int bucket = whichBucket(key);
		// 2. Get the list of entries in that bucket:
		EntryNode start = this.buckets.getIndex(bucket);

		// 3. Search the list for the pair we want, and delete it. (Remember: Singly-Linked-List removeIndex).
		EntryNode previous = null;
		for (EntryNode current = start; current != null; current = current.next) {
			if (current.matches(key)) {
				// 3.a. If we find it, remove the node and return the current value.
				if (previous == null) {
					// 3.a.1. it was the first node: (removeFront)
					this.buckets.setIndex(bucket, current.next);
				} else {
					// 3.a.2. it wasn't:
					previous.next = current.next;
				}

				// 3.a.3. Update sizes & maybe shrink buckets...
				if (this.buckets.getIndex(bucket) == null) {
					this.numUsedBuckets -= 1;
				}
				this.size -= 1;
				this.maybeShrink();

				// 3.a.4. Return value:
				return current.value;
			}
			// Update previous as we go around...
			previous = current;
		}
		// 3.b. If we don't find it, return null!
		return null;
	}

	@Override
	public ListADT<String> getKeys() {
		ListADT<String> keys = new JavaList<>();
		for (int i=0; i<buckets.size(); i++) {
			for (EntryNode n = buckets.getIndex(i); n != null; n = n.next) {
				keys.addBack(n.key);
			}
		}
		return keys;
	}

	@Override
	public ListADT<Pair<String, Integer>> getEntries() {
		ListADT<Pair<String, Integer>> entries = new JavaList<>();
		for (int i=0; i<buckets.size(); i++) {
			for (EntryNode n = buckets.getIndex(i); n != null; n = n.next) {
				entries.addBack(n.getPair());
			}
		}
		return entries;
	}

	/**
	 * This is a "singly-linked-list" node of HashTable entries.
	 */
	public static class EntryNode {
		/**
		 * The key from put, remove, get, etc.
		 */
		String key;
		/**
		 * The value stored for this key.
		 */
		int value;
		/**
		 * The next item after this one in the same bucket.
		 */
		EntryNode next;

		public EntryNode(String key, int value, EntryNode next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Helper method for comparing keys when searching.
		 * @param key - the key we're looking for.
		 * @return true if it matches the key in this EntryNode.
		 */
		public boolean matches(String key) {
			// Checking the hash-codes first often is faster:
			return this.key.hashCode() == key.hashCode() && this.key.equals(key);
		}

		/**
		 * Helper method for getEntries(): turn this into a Pair object.
		 * @return (key, value)
		 */
		public Pair<String, Integer> getPair() {
			return new Pair<>(this.key, this.value);
		}
	}
}
