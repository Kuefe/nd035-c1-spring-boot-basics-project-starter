package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES(filename, contenttype, filesize, filedata, userid) VALUES(#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES")
    List<File> getFiles();

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileByFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFileByUserid(Integer userid);

    @Select("SELECT count(*) FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    int checkFilename(Integer userid, String filename);

    @Update("UPDATE FILES SET " +
            "filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize}, filedata=#{filedata}" +
            "WHERE fileId = #{fileId}")
    int updateFileByFileId(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFileByFileId(Integer fileId);

    @Delete("DELETE FROM FILES WHERE userid = #{userid}")
    int deleteFileByUserid(Integer userid);
}
