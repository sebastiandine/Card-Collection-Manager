package org.sebastiandine.cardcollectionmanager.container;

import org.junit.Test;
import org.sebastiandine.cardcollectionmanager.bean.SetBean;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SetBeanContainerTest {
	
	@Test
	public void testSetApiAlive(){
		SetBeanContainer.updateSetBeanListFromApi();
		assertThat(SetBeanContainer.getSetBeanList(), is(arrayWithSize(greaterThan(0))));
	}
	
	@Test
	public void testUpdateSetBeanListFromApi(){
		SetBean oldLatestSet = SetBeanContainer.getLatestSet();
		SetBean[] oldSetArray = Arrays.copyOf(SetBeanContainer.getSetBeanList(), SetBeanContainer.getSetBeanList().length); 
		int oldSetCount = oldSetArray.length;
		
		SetBeanContainer.updateSetBeanListFromApi();
		
		SetBean newLatestSet = SetBeanContainer.getLatestSet();
		int newSetCount = SetBeanContainer.getSetBeanList().length;
		
		//smaller set count is not allowed
		assertThat(newSetCount, is(greaterThanOrEqualTo(oldSetCount)));
		
		//option 1 - same set count -> latest set must be the same as before
		if(oldSetCount == newSetCount){
			assertThat(newLatestSet.getCode(), is(equalTo(oldLatestSet.getCode())));
		}
		/* option 2 - higher set count
		 * since older sets might have been added, we cannot simply check, if the 
		 * new latest set is different from the old one.
		 * We therefore check, if all sets before the update are still in the new
		 * list of sets.
		 */
		if(newSetCount > oldSetCount){
			assertThat(SetBeanContainer.getSetBeanList(),is(arrayContainingInAnyOrder(oldSetArray)));
		}
	}
	
	@Test
	public void testGetLatestSet(){
		SetBean latestSet = SetBeanContainer.getSetBeanList()[SetBeanContainer.getSetBeanList().length-1];
		assertThat(SetBeanContainer.getLatestSet(), is(equalTo(latestSet)));
	}

}
