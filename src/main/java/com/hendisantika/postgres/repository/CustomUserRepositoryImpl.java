package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

@Transactional
public class CustomUserRepositoryImpl implements CustomUserRepository {


    private static final String INSERT_QUERY =
            "INSERT INTO cysec.users ( id, username,  name, course, pass,auth_token) " +
                    "VALUES (:id,:username, :name, :course, :password,:auth_token) ";

    private static final String INSERT_QUERY_API_3 =
            "INSERT INTO cysec.api_3_user ( id, username,  latitude, longitude) " +
                    "VALUES (:id,:username, :latitude, :longitude) ";

    private static final String INSERT_COMMENT_QUERY_API_3 =
            "INSERT INTO cysec.api_3_comment ( id, username,  comment) " +
                    "VALUES (:id,:username, :comment) ";



    private static final String INSERT_AUTH_TOKEN =
            "INSERT INTO cysec.two_factor_auth ( id,username,  otp) " +
                    "VALUES (:id,:username, :otp) ";


    private static final String LOGIN_QUERY = "Select auth_token from users where username = :username and pass= :pass ";


    private static final String FIND_QUERY = "Select * from cysec.users where id = :id ";

    private static final String FIND_QUERY_USERNAME = "Select user from cysec.users where username = :username ";

    private static final String FIND_ALL_QUERY = "Select * from cysec.users ";


    private static final String GET_AUTH_TOKENS = "Select auth_token from cysec.users";

    private static final String GET_AUTH_TOKEN = "Select auth_token from cysec.users where auth_token = :auth_token";


    @PersistenceContext
    private EntityManager entityManager;

    //@Override
    //public User customFindMethod(Long id) {
    //  return (User) entityManager.createQuery()
    //        .setParameter("id", id)
    //      .getSingleResult();
    //}


    @Override
    public User customFindMethod(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public Api3User customFindMethod4Api3(Long id) {
        try {
            Api3User user = entityManager.find(Api3User.class, id);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Api7User customFindMethod4Api7(Long id) {
        try {
            Api7User user = entityManager.find(Api7User.class, id);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Api6User customFindMethod4Api6(Long id) {
        try {
            Api6User user = entityManager.find(Api6User.class, id);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Api3User customFindMethod4Api3Username(String username) {
        return null;
    }

    @Override
    public List<User> customFindAllMethod(String auth_token) {
        try {
            return entityManager.createNativeQuery(FIND_ALL_QUERY)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    @Override
    public List<String> getAllTokens() {
        return entityManager.createNativeQuery(GET_AUTH_TOKENS).getResultList();
    }

    @Override
    public String checkIsValidUser(User s) {
        try {
            return entityManager.createNativeQuery("Select auth_token from users where username = " + s.getUsername() + " and pass =" + s.getPass())
                    .getSingleResult().toString();


            // List<User> users =    entityManager.createQuery(LOGIN_QUERY,User.class)
            //       .setParameter("username", s.getUsername())
            //    .setParameter("password", s.getPassword()).getResultList();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List checkIsValidUserApi8(User s) {
        try {
            String a ="Select * from users  where username = " + "\'"+  s.getUsername() + "\'"
                    + " and pass =" + "\'"+s.getPass() +"\'";
            List<Object> e = entityManager.createNativeQuery(a).getResultList();
            return e;


            // List<User> users =    entityManager.createQuery(LOGIN_QUERY,User.class)
            //       .setParameter("username", s.getUsername())
            //    .setParameter("password", s.getPassword()).getResultList();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public Boolean createOtp(String userName) {
        int deletedCount = 0;
        BigInteger findByIdCount1 = (BigInteger) entityManager.createNativeQuery("select count(tfa) FROM cysec.two_factor_auth as tfa where tfa.username=:username")
                .setParameter("username", userName).getSingleResult();
        int findByIdCount = findByIdCount1.intValue();
        try {
            if (findByIdCount>0) {
                deletedCount = entityManager.createQuery("DELETE FROM two_factor_auth where username=:username").setParameter("username", userName).executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (deletedCount > 0 || findByIdCount == 0L) {
            Random rand = new Random();
            String id = String.format("%02d", rand.nextInt(100));
            int x = Integer.valueOf(id);
            entityManager.createNativeQuery(INSERT_AUTH_TOKEN)
                    .setParameter("id",x)
                    .setParameter("username", userName)
                    .setParameter("otp", x).executeUpdate();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean verifyOtp(VerifyOtp otp) {
        BigInteger findByIdCount = (BigInteger) entityManager.createNativeQuery("select count(tfa) FROM cysec.two_factor_auth as tfa where tfa.otp=:otp and tfa.username = :username")
                .setParameter("otp", otp.getOtp())
                .setParameter("username",otp.getUsername()).getSingleResult();
        int x = findByIdCount.intValue();

        if(x == 1){
            return true;
        }else {
            return false;
        }

    }


    @Override
    @Transactional
    public void create(User s) {
        try {
            entityManager.createNativeQuery(INSERT_QUERY)
                    .setParameter("id", s.getId())
                    .setParameter("username", s.getUsername())
                    .setParameter("name", s.getName())
                    .setParameter("course", s.getCourse())
                    .setParameter("password", s.getPass())
                    .setParameter("auth_token", s.getAuthToken()).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println();
    }

    @Override
    public int  create4Api3(Api3User s) {

        try {
           int result = entityManager.createNativeQuery(INSERT_QUERY_API_3)
                    .setParameter("id", s.getId())
                    .setParameter("username", s.getUsername())
                    .setParameter("latitude",s.getLatitude())
                    .setParameter("longitude",s.getLongitude()).executeUpdate();
        return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        //System.out.println();
    }

    @Override
    public void createComment4Api3(Api3Comment s) {
        try {
            entityManager.createNativeQuery(INSERT_COMMENT_QUERY_API_3)
                    .setParameter("id", s.getId())
                    .setParameter("username", s.getUsername())
                    .setParameter("comment",s.getComment()).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final String INSERT_COMMENT_QUERY_API_5 =
            "INSERT INTO cysec.api_5_user ( id, username,  pass, name, address, mobile_no,auth_token,user_role) " +
                    "VALUES (:id,:username, :pass,:name,:address,:mobileno,:auth_token,:user_role) ";
    @Override
    public int create4Api5(Api5User s) {

        try {
            String authToken = s.getName() + ":" + s.getPass();
            String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
            entityManager.createNativeQuery(INSERT_COMMENT_QUERY_API_5)
                    .setParameter("id", s.getId())
                    .setParameter("username", s.getUsername())
                    .setParameter("pass",s.getPass())
                    .setParameter("name",s.getName())
                    .setParameter("mobileno",s.getMobileNo())
                    .setParameter("auth_token",encodedString)
                    .setParameter("address",s.getAddress())
                    .setParameter("user_role",s.getUser_role()).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return 0;
    }

    private static final String INSERT_QUERY_API_6 =
            "INSERT INTO cysec.api_5_user ( id, name,  username, pass, credit) " +
                    "VALUES (:id,:name, :username,:pass,:credit) ";
    @Override
    public int create4Api6(Api6User s) {

        try {
            return entityManager.createNativeQuery(INSERT_QUERY_API_6)
                    .setParameter("id", s.getId())
                    .setParameter("name", s.getName())
                    .setParameter("username",s.getUsername())
                    .setParameter("pass",s.getPass())
                    .setParameter("credit",s.getCredit())
                    .executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return 0;
    }


    private static final String INSERT_QUERY_API_7 =
            "INSERT INTO cysec.api_7_user ( id, name,  username, pass,session_id,auth_token) " +
                    "VALUES (:id,:name, :username,:pass,:session_id,:auth_token) ";

    @Override
    public int create4Api7(Api7User s) {
        String authToken = s.getName() + ":" + s.getPass();
        String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        String sessionId = s.getName() + s.getUsername() + System.currentTimeMillis();
        try {
            return entityManager.createNativeQuery(INSERT_QUERY_API_7)
                    .setParameter("id", s.getId())
                    .setParameter("name", s.getName())
                    .setParameter("username",s.getUsername())
                    .setParameter("pass",s.getPass())
                    .setParameter("session_id",null)
                    .setParameter("auth_token",encodedString.trim())
                    //.setParameter("session_id",sessionId)
                    .executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return 0;
    }



    @Override
    public String checkIsValidUser(String auth_token) {
        String s = "";
        try {

            s = entityManager.createNativeQuery(GET_AUTH_TOKEN)
                    .setParameter("auth_token", auth_token)
                    .getSingleResult().toString();
        } catch (NoResultException nre) {

        }

        if (s == null || s.equals("")) {
            return null;
        } else {
            return s;
        }

        // List<User> users =    entityManager.createQuery(LOGIN_QUERY,User.class)
        //       .setParameter("username", s.getUsername())
        //    .setParameter("password", s.getPassword()).getResultList()

    }


    private static final String LOGIN_QUERY_4_API_7 = "Select username from cysec.api_7_user where username = :username and pass= :pass ";


    @Override
    public String checkIsValidUserApi7(Api7User user) {
        String s = "";
        try {

            s = entityManager.createNativeQuery(LOGIN_QUERY_4_API_7)
                    .setParameter("username", user.getUsername())
                    .setParameter("pass",user.getPass())
                    .getSingleResult().toString();
        } catch (NoResultException nre) {

        }

        if (s == null || s.equals("")) {
            return null;
        } else {
            return s;
        }

        // List<User> users =    entityManager.createQuery(LOGIN_QUERY,User.class)
        //       .setParameter("username", s.getUsername())
        //    .setParameter("password", s.getPassword()).getResultList()

    }

    private static final String CREATE_SESSION_QUERY_4_API_7 = "UPDATE cysec.api_7_user SET session_id = :session_id where username = :username ";


    @Override
    public int createSession(String username) {
        String sessionId = username + System.currentTimeMillis();
        try {
            return entityManager.createNativeQuery(CREATE_SESSION_QUERY_4_API_7)
                    .setParameter("session_id", sessionId)
                    .setParameter("username", username)
                    .executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    private static final String INSERT_QUERY_API_9 =
            "INSERT INTO cysec.api_9_user ( id, username, pin,try_count) " +
                    "VALUES (:id,:username,:pin,:try_count) ";

    @Override
    public int create4Api9(Api9User s) {
        try {
            return entityManager.createNativeQuery(INSERT_QUERY_API_9)
                    .setParameter("id", s.getId())
                    .setParameter("username", s.getUsername())
                    .setParameter("pin",s.getPin())
                    .setParameter("try_count",s.getTryCount())
                    //.setParameter("session_id",sessionId)
                    .executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    private static final String LOGIN_QUERY_4_API_9_1 = "Select a.username from cysec.api_9_user a where username = :username and pin=:pin ";

    @Override
    public String login4Api9_1(Api9User s) {
        try {

            return entityManager.createNativeQuery(LOGIN_QUERY_4_API_9_1)
                    .setParameter("username", s.getUsername())
                    .setParameter("pin",s.getPin()).getSingleResult().toString();
        } catch (NoResultException nre) {
            return null;
        }
    }

    private static final String LOGIN_QUERY_4_API_9_2 = "Select a.username from cysec.api_9_user a where username = :username and pin=:pin and 5 > try_count ";
    @Override
    public String login4Api9_2(Api9User s) {
        try {

            return entityManager.createNativeQuery(LOGIN_QUERY_4_API_9_2)
                    .setParameter("username", s.getUsername())
                    .setParameter("pin",s.getPin()).getSingleResult().toString();
        } catch (NoResultException nre) {
            return null;
        }
    }

    private static final String Update_QUERY_4_API_9_2 = "Update cysec.api_9_user set try_count=:try_count where username = :username and 5 > :try_count ";

    @Override
    public int updateTryCount4Api9(String s,Long a) {
        try {

            return entityManager.createNativeQuery(Update_QUERY_4_API_9_2)
                    .setParameter("username", s)
                    .setParameter("try_count",a).executeUpdate();
        } catch (NoResultException nre) {
            return 0;
        }
    }


}
