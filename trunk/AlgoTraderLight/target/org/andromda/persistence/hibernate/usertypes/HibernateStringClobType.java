// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateStringClobType.vsl in andromda-hibernate-cartridge.
//
package org.andromda.persistence.hibernate.usertypes;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * <p>
 * A hibernate user type which converts a Clob into a String and back again.
 * </p>
 */
public class HibernateStringClobType
        implements UserType {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long	serialVersionUID	= 10000L;
	
	/**
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	@Override
	public int[] sqlTypes() {
		return new int[] {
			Types.CLOB
		};
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	@Override
	public Class returnedClass() {
		return String.class;
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public boolean equals(
	        final Object x,
	        final Object y)
	        throws HibernateException {
		boolean equal = false;
		if (x == null || y == null) {
			equal = false;
		} else if (!(x instanceof String) || !(y instanceof String)) {
			equal = false;
		} else {
			equal = ((String) x).equals(y);
		}
		return equal;
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
	 *      java.lang.String[], java.lang.Object)
	 */
	@Override
	public Object nullSafeGet(
	        final ResultSet resultSet,
	        final String[] names,
	        final Object owner)
	        throws HibernateException, SQLException {
		final StringBuffer buffer = new StringBuffer();
		try {
			// First we get the stream
			final Reader inputStream = resultSet.getCharacterStream(names[0]);
			if (inputStream == null) { return null; }
			final char[] buf = new char[1024];
			int read = -1;
			
			while ((read = inputStream.read(buf)) > 0) {
				buffer.append(new String(
				        buf,
				        0,
				        read));
			}
			inputStream.close();
		} catch (final IOException exception) {
			throw new HibernateException("Unable to read from resultset",
			        exception);
		}
		return buffer.toString();
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement,
	 *      java.lang.Object, int)
	 */
	@Override
	public void nullSafeSet(
	        final PreparedStatement preparedStatement,
	        final Object data,
	        final int index)
	        throws HibernateException, SQLException {
		if (data != null) {
			final StringReader r = new StringReader((String) data);
			preparedStatement.setCharacterStream(
			        index,
			        r,
			        ((String) data).length());
		} else {
			preparedStatement.setNull(
			        index,
			        sqlTypes()[0]);
		}
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	@Override
	public Object deepCopy(Object value)
	        throws HibernateException {
		String ret = null;
		value = value == null ? new String() : value;
		final String in = (String) value;
		final int len = in.length();
		final char[] buf = new char[len];
		
		for (int i = 0; i < len; i++) {
			buf[i] = in.charAt(i);
		}
		ret = new String(buf);
		return ret;
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	@Override
	public boolean isMutable() {
		return false;
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#replace(Object original, Object
	 *      target, Object owner)
	 */
	@Override
	public Object replace(final Object original, final Object target,
	        final Object owner) {
		return deepCopy(original);
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable
	 *      cached, Object owner)
	 */
	@Override
	public Object
	        assemble(final java.io.Serializable cached, final Object owner) {
		return deepCopy(cached);
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#assemble(Object value)
	 */
	@Override
	public java.io.Serializable disassemble(final Object value) {
		return (java.io.Serializable) value;
	}
	
	/**
	 * @see org.hibernate.usertype.UserType#assemble(Object value)
	 */
	@Override
	public int hashCode(final Object x) {
		return x.hashCode();
	}
}