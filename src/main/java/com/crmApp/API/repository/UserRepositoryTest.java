package com.crmApp.API.repository;



import com.crmApp.API.controller.UserRESTController;
import com.crmApp.API.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Before
    public void setUp() throws Exception {
        User user1= new User("Romain","Deprez","2 rue de Paris","romain@email.fr","romain123");
        User user2= new User("Leonel","Rosas", "2 rue de moufetard", "leonel@email.fr", "leonel123");
        //save user, verify has ID value after save
        assertNull(user1.getUserId());
        assertNull(user2.getUserId());//null before save
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        assertNotNull(user1.getUserId());
        assertNotNull(user2.getUserId());
    }
//    @Test
//    public void testFetchData(){
//        /*Test data retrieval*/
//        User userA = UserRESTController.getUserByEmail("Romain@email.fr");
//        assertNotNull(userA);
//        assertEquals("romain@email.fr", userA.getEmail());
//        /*Get all products, list should only have two*/
//        Iterable<User> users = userRepository.findAll();
//        int count = 0;
//        for(User p : users){
//            count++;
//        }
//        assertEquals(count, 2);
//    }
}
