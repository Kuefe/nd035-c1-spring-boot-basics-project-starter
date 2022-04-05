package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredentialBycredentialid(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialByUserid(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredentialByUsername(String username);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials();

    @Update("UPDATE CREDENTIALS SET password = #{password} WHERE userid = #{userid}")
    int updatePasswordByUserid(Credential credential);

    @Update("UPDATE CREDENTIALS SET " +
            "url=#{url}, username=#{username}, password = #{password} " +
            "WHERE credentialid = #{credentialid}")
    int updateByCredentialId(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredentialByCredentialid(Integer credentialid);

    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userid}")
    int deleteCredentialByUserid(Integer userid);

    @Delete("DELETE FROM CREDENTIALS WHERE username = #{username}")
    int deleteCredentialByUsername(String username);
}
