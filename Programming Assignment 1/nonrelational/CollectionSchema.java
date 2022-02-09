package hdb.data.nonrelational;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A {@code CollectionSchema} represents the schema of a non-relational collection.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class CollectionSchema implements java.io.Serializable {

	/**
	 * An {@code InvalidAttributeIndexException} is thrown if an invalid attribute index is given to a
	 * {@code CollectionSchema}.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class InvalidAttributeIndexException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = -7371027889948222798L;

	}

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -100208280521684870L;

	/**
	 * A {@code HashMap} that associates each attribute name with an attribute index.
	 */
	HashMap<String, Integer> name2index = new HashMap<String, Integer>();

	/**
	 * A {@code HashMap} that associates each attribute index with an attribute name.
	 */
	HashMap<Integer, String> index2name = new HashMap<Integer, String>();

	/**
	 * A {@code HashMap} that associates attribute indices with {@code CollectionSchema}s.
	 */
	HashMap<Integer, CollectionSchema> index2schema = new HashMap<Integer, CollectionSchema>();

	/**
	 * Constructs a {@code CollectionSchema}.
	 */
	public CollectionSchema() {
	}

	/**
	 * Returns a string representation of this {@code CollectionSchema}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, String> e : index2name.entrySet()) {
			s += (s.length() == 1 ? "" : ", ") + e.getKey() + "=" + e.getValue();
			CollectionSchema c = index2schema.get(e.getKey());
			if (c != null)
				s += c.toString();
		}
		return s + "}";
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 */
	public String attributeName(int attributeIndex) {
		return index2name.get(attributeIndex);
	}

	/**
	 * Returns the subschema associated with the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the subschema associated with the specified attribute
	 */
	public CollectionSchema subschema(int attributeIndex) {
		return index2schema.get(attributeIndex);
	}

	/**
	 * Returns the index of the specified attribute in this {@code CollectionSchema} (needs to register that attribute
	 * if no such attribute has been registered in this {@code CollectionSchema}).
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @return the index of the specified attribute in this {@code CollectionSchema}
	 */
	public int[] attributeIndex(String attributeName) {
		// TODO complete this method
		int[] attrIndex;
		
		if(attributeName.indexOf('.') > 0) {																								// hierarchical data expected
			attrIndex = new int[2];																											// array of size 2 if hierarchical data is expected
			attrIndex[0] = this.attributeIndex(attributeName.substring(0, attributeName.indexOf('.')))[0];									// index of the subschema in index2schema map
			if(this.subschema(attrIndex[0]) == null)																						// if the subschema does not exist, create it
				this.index2schema.put(attrIndex[0], new CollectionSchema());
			attrIndex[1] = (this.subschema(attrIndex[0])).attributeIndex(attributeName.substring(attributeName.indexOf('.') + 1))[0]; 		// find the sub-attribute. if it does not exist, add it and return the new index
		} else {																															// hierarchical data not expected
			attrIndex = new int[1];																											
			if(this.name2index.get(attributeName) != null)																					// if the attribute exists
				attrIndex[0] = this.name2index.get(attributeName);																			// get the index returned from the HashMap and set to the array
			else {
				int newIndex = this.name2index.size();																						// statically save the index of the new attribute
				
				this.name2index.put(attributeName, newIndex);																				// map the attributeName to its index
				this.index2name.put(newIndex, attributeName);																				// map the new index to its attributeName
				attrIndex[0] = newIndex;																									// set the return arrays value at index 0 to the new index
			}
		}
		return attrIndex;
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if the specified attribute index is invalid
	 */
	public String attributeName(int[] attributeIndex) throws InvalidAttributeIndexException {
		// TODO complete this method
		if(attributeIndex.length > 1) {																					// expect to return hierarchical data if the attributeIndex is more than 1 element														
			if(this.subschema(attributeIndex[0]) == null)																// check to see if the subschema exists
				throw new InvalidAttributeIndexException();
			else																										// return the main attribute concatenated with the sub-attribute
				return this.index2name.get(attributeIndex[0]) + "." + this.subschema(attributeIndex[0]).attributeName(attributeIndex[1]);
		} else {
			if(this.index2name.get(attributeIndex[0]) == null)															// if the attribute at a specified index doesn't exist, throw an exception
				throw new InvalidAttributeIndexException();
			else																										// otherwise, return the attribute name at the given index
				return this.index2name.get(attributeIndex[0]);
		}
	}

}
