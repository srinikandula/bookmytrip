package test.com.bookmytrip;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.Mockito;

import junit.framework.Assert;

import org.junit.Test;

import com.bookmytrip.dao.UserDAO;
import com.bookmytrip.domain.User;
import com.bookmytrip.service.impl.UserServiceImpl;

public class TestUserServiceImpl {

	@Test
	public void runTest(){
		User u1 = new User();
		User u2 = new User();
		UserServiceImpl mockedService = mock(UserServiceImpl.class);
		UserDAO mockDao = mock(UserDAO.class);
		mockedService.setDao(mockDao);
		User u = new User();
		u.setId(10);
		when(mockDao.createUser(u)).thenReturn(u.getId());
		
		
		
		
		
		Assert.assertEquals(new Integer(0),mockedService.saveUser(null));
		Assert.assertEquals(new Integer(10),mockedService.saveUser(u));
		
		when(mockedService.getDao()).thenReturn(mockDao);
		mockedService.saveUser(null);
		
		Mockito.verify(mockedService).setDao(null);
		Mockito.when(mockedService.saveUser(u1)).thenReturn(1);
		Mockito.when(mockedService.saveUser(u2)).thenReturn(2);
		Assert.assertEquals(1,(int)mockedService.saveUser(u1));
		Assert.assertEquals(2,(int)mockedService.saveUser(u2));
		Assert.assertFalse(mockedService.saveUser(u2) == 1);
	}
	
}
