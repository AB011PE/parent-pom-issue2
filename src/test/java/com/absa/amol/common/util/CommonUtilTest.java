package com.absa.amol.common.util;


import static com.absa.amol.common.util.CollectionUtil.isNotNullAndNotEmpty;
import static com.absa.amol.common.util.CollectionUtil.isNullOrEmpty;
import static com.absa.amol.common.util.CommonUtil.isNotNull;
import static com.absa.amol.common.util.CommonUtil.isNull;
import static com.absa.amol.common.util.StringUtil.getStringLength;
import static com.absa.amol.common.util.StringUtil.isStringNotNullAndNotEmpty;
import static com.absa.amol.common.util.StringUtil.isStringNullOrEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class CommonUtilTest {

	List<String> testData=Arrays.asList(null,"null","","Test");
	List emptyList=Collections.EMPTY_LIST;
	List nullList=null;
			
	@Test
	public void testIsNullWithNull() {
		assertTrue(isNull(testData.get(0)));
	}
	
	@Test
	public void testIsNullWithNotNullObject() {
		assertFalse(isNull(testData));
	}
	
	@Test
	public void testIsNullWithNullString() {
		assertFalse(isNull(testData.get(1)));
	}
	
	@Test
	public void testIsNullWithNulEmptyString() {
		assertFalse(isNull(testData.get(2)));
	}
	
	@Test
	public void testIsNotNullWithNull() {
		assertFalse(isNotNull(testData.get(0)));
	}
	
	@Test
	public void testIsNotNullWithNotNullObject() {
		assertTrue(isNotNull(testData));
	}
	
	@Test
	public void testIsNotNullWithNullString() {
		assertTrue(isNotNull(testData.get(1)));
	}
	
	@Test
	public void testIsNotNullWithNulEmptyString() {
		assertTrue(isNotNull(testData.get(2)));
	}
	
	@Test
	public void testIsNotNullAndNotEmptyWithNullList() {
		assertFalse(isNotNullAndNotEmpty(nullList));
	}
	
	@Test
	public void testIsNotNullAndNotEmptyWithEmptyList() {
		assertFalse(isNotNullAndNotEmpty(emptyList));
	}
	
	@Test
	public void testIsNotNullAndNotEmptyWithNonEmptyAndNullList() {
		assertTrue(isNotNullAndNotEmpty(testData));
	}
	
	@Test
	public void testIsNullOrEmptyWithNullList() {
		assertTrue(isNullOrEmpty(nullList));
	}
	
	@Test
	public void testIsNullOrEmptyWithEmptyList() {
		assertTrue(isNullOrEmpty(emptyList));
	}
	
	@Test
	public void testIsNullOrEmptyWithNonEmptyAndNullList() {
		assertFalse(isNullOrEmpty(testData));
	}
	
	@Test
	public void testIsStringNotNullAndNotEmptyWithNotNullSring() {
		assertTrue(isStringNotNullAndNotEmpty(testData.get(1)));
	}
	
	@Test
	public void testIsStringNotNullAndNotEmptyWithEmptySring() {
		assertFalse(isStringNotNullAndNotEmpty(testData.get(2)));
	}
	
	@Test
	public void testIsStringNotNullAndNotEmptyWithNullSring() {
		assertFalse(isStringNotNullAndNotEmpty(testData.get(0)));
	}
	
	@Test
	public void testIsStringNullOrEmptyWithNotNullSring() {
		assertFalse(isStringNullOrEmpty(testData.get(1)));
	}
	
	@Test
	public void testIsStringNullOrEmptyWithEmptySring() {
		assertTrue(isStringNullOrEmpty(testData.get(2)));
	}
	
	@Test
	public void testIsStringNullOrEmptyWithNullSring() {
		assertTrue(isStringNullOrEmpty(testData.get(0)));
	}
	
	@Test
	public void testGetStringLengthWithNotNullSring() {
		assertEquals(4,getStringLength(testData.get(1)));
	}
	
	@Test
	public void testGetStringLengthWithEmptySring() {
		assertEquals(0,getStringLength(testData.get(2)));
	}
	
	@Test
	public void testGetStringLengthWithNullSring() {
		assertEquals(0,getStringLength(testData.get(0)));
	}
	
}
