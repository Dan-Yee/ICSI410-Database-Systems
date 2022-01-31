package hdb.data.nonrelational;

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
		return null;
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
		return null;
	}

}
