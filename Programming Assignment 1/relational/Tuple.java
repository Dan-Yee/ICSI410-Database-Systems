package hdb.data.relational;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import hdb.data.relational.RelationSchema.InvalidAttributeIndexException;

/**
 * A {@code Tuple} represents a record in a relation containing a number of attributes.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Tuple implements java.io.Serializable {

	/**
	 * A {@code TypeException} is thrown when an object is not an instance of an appropriate type.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class TypeException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = 2260118532930630008L;

	}

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -2038398067728844490L;

	/**
	 * The {@code RelationSchema} for this {@code Tuple}.
	 */
	RelationSchema schema;

	/**
	 * The attribute values of this {@code Tuple}.
	 */
	Object[] attributeValues;

	/**
	 * Constructs a {@code Tuple}.
	 * 
	 * @param schema
	 *            a {@code RelationSchema}
	 * @param attributeValues
	 *            the attribute values of the {@code Tuple}
	 * @throws TypeException
	 *             if a specified attribute value does not match the type of the corresponding attribute
	 */
	public Tuple(RelationSchema schema, Object... attributeValues) throws TypeException {
		this.schema = schema;
		this.attributeValues = new Object[schema.attributeTypes.length];
		for (int i = 0; i < schema.attributeTypes.length; i++)
			try {
				setAttribute(i, attributeValues[i]);
			} catch (InvalidAttributeIndexException e) {
				throw new TypeException();
			}
	}

	/**
	 * Returns the value of the specified attribute.
	 * 
	 * @param i
	 *            the index of an attribute
	 * @return the value of the specified attribute
	 */
	public Object attributeValue(int i) {
		return attributeValues[i];
	}

	/**
	 * Returns a string representation of this {@code Tuple}.
	 */
	@Override
	public String toString() {
		return Arrays.toString(attributeValues);
	}

	/**
	 * Sets the value of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @param o
	 *            the value of the attribute
	 * @throws TypeException
	 *             if the specified object is not an instance of the type of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given
	 */
	public void setAttribute(int attributeIndex, Object o) throws TypeException, InvalidAttributeIndexException {
		// TODO complete this method
		if(attributeIndex < 0 || attributeIndex > this.schema.attributeTypes.length - 1)								// bounds checking to make sure attributeIndex is in range of the array
			throw new InvalidAttributeIndexException();
		if(this.schema.attributeTypes[attributeIndex].isInstance(o))													// type checking to make sure o is of the correct type
			this.attributeValues[attributeIndex] = o;
		else
			throw new TypeException();
	}

	/**
	 * Writes the attributes of this {@code Tuple} to the specified {@code ObjectOutputStream}.
	 * 
	 * @param out
	 *            an {@code ObjectOutputStream}.
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void writeAttributes(ObjectOutputStream out) throws IOException {
		// TODO complete this method
		for(Object o : this.attributeValues)																			// write every object through the ObjectOutputStream
			this.write(o, out);
	}

	/**
	 * Constructs a {@code Tuple} from the specified {@code ObjectInputStream}.
	 * 
	 * @param schema
	 *            a {@code RelationSchema}
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @throws TypeException
	 *             if an attribute value does not match the type of the corresponding attribute
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 * @throws InvalidAttributeIndexException
	 *             if an invalid attribute index is given
	 */
	public Tuple(RelationSchema schema, ObjectInputStream in)
			throws ClassNotFoundException, TypeException, IOException, InvalidAttributeIndexException {
		this.schema = schema;
		attributeValues = new Object[schema.attributeTypes.length];
		for (int i = 0; i < schema.attributeTypes.length; i++)
			setAttribute(i, read(schema.attributeType(i), in));
	}

	/**
	 * Writes the specified object to the specified {@code ObjectOutputStream}.
	 * 
	 * @param o
	 *            an object
	 * @param out
	 *            an {@code ObjectOutputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void write(Object o, ObjectOutputStream out) throws IOException {
		// TODO complete this method
		if(o.getClass().equals(Integer.class))																			// use ObjectOutputStream#writeInt if o is an Integer
			out.writeInt((Integer)o);
		else if(o.getClass().equals(Double.class))																		// use ObjectOutputStream#writeDouble if o is a Double
			out.writeDouble((Double)o);
		else																											// use ObjectOutputStream#writeObject otherwise
			out.writeObject(o);
	}

	/**
	 * Reads an object of the specified type from the specified {@code ObjectInputStream}.
	 * 
	 * @param type
	 *            a type
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @return the object read
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 */
	protected Object read(Class<?> type, ObjectInputStream in) throws IOException, ClassNotFoundException {
		// TODO complete this method		
		if(type.equals(Integer.class))																					// use ObjectInputStream#readInt if type is an Integer
			return in.readInt();
		else if(type.equals(Double.class))																				// use ObjectInputStream#readDouble if type is a Double
			return in.readDouble();
		else																											// use ObjectInputStream#readObject otherwise
			return in.readObject();
	}
}
