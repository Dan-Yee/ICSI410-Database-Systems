package hdb.data.nonrelational;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import hdb.data.nonrelational.CollectionSchema.InvalidAttributeIndexException;

/**
 * A {@code DataObject} represents an entity using a number of attributes organized possibly in a hierarchical fashion.
 * {@code DataObject}s in a non-relational collection can have different sets of attributes.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class DataObject implements java.io.Serializable {

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -5147919692261347227L;

	/**
	 * The {@code CollectionSchema} for this {@code DataObject}.
	 */
	CollectionSchema schema;

	/**
	 * A {@code HashMap} that associates each attribute index with a value.
	 */
	HashMap<Integer, Object> index2value = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code DataObject}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 */
	public DataObject(CollectionSchema schema) {
		this.schema = schema;
	}

	/**
	 * Returns the {@code CollectionSchema} associated this {@code DataObject}.
	 * 
	 * @return the {@code CollectionSchema} associated this {@code DataObject}
	 */
	public CollectionSchema schema() {
		return schema;
	}

	/**
	 * Returns a string representation of this {@code DataObject}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, Object> e : index2value.entrySet()) {
			s += (s.length() == 1 ? "" : ", ");
			s += (e.getKey() + ":" + schema.attributeName(e.getKey()) + "=" + e.getValue());
		}
		return s + "}";
	}

	/**
	 * Sets the value of the specified attribute.
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @param o
	 *            the value of the attribute
	 */
	public void setAttribute(String attributeName, Object o) {
		// TODO complete this method
		int[] index = this.schema.attributeIndex(attributeName);
		
		if(index.length > 1) {
			//this.schema.subschema(index[0]).index2name.put(index[1], );
			//System.out.println("Subschema index2name: " + this.schema.subschema(index[0]).index2name.toString());
			this.index2value.put(index[0], this.schema.subschema(index[0]));
		} else {
			this.index2value.put(index[0], o);
		}
	}

	/**
	 * Returns the value of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the value of the specified attribute
	 */
	public Object attributeValue(int[] attributeIndex) {
		// TODO complete this method
		if(attributeIndex.length > 1) {
			return 1;
		} else
			return this.index2value.get(attributeIndex[0]);
	}

	/**
	 * Writes the attributes of this {@code DataObject} to the specified {@code ObjectOutputStream}.
	 * 
	 * @param out
	 *            an {@code ObjectOutputStream}.
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void writeAttributes(ObjectOutputStream out) throws IOException {
		// TODO complete this method
		for(Object value : this.index2value.values())																// write the attribute value to the ObjectOutputStream
			out.writeObject(value);
	}

	/**
	 * Constructs a {@code DataObject} from the specified {@code ObjectInputStream}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 * @throws InvalidAttributeIndexException
	 *             if an attribute not registered in the {@code CollectionSchema} is read
	 */
	public DataObject(CollectionSchema schema, ObjectInputStream in)
			throws IOException, ClassNotFoundException, InvalidAttributeIndexException {
		// TODO complete this method
		this.schema = schema;																						// set the CollectionSchema for this DataObject
		
		try {
			for(int i = 0;; i++) {																					// continue to read objects from the ObjectInputStream until EOFException is thrown
				Object value = in.readObject();
				if(this.schema.attributeName(i) == null)															// check to see if the attribute at the index is registered 
					throw new InvalidAttributeIndexException();
				else																								// set the attribute read from the input stream assuming the data was given in the correct order
					this.setAttribute(this.schema.attributeName(i), value);
			}
		} catch(EOFException e) {
			System.out.println("End of ObjectInputStream");
		}
	}
}