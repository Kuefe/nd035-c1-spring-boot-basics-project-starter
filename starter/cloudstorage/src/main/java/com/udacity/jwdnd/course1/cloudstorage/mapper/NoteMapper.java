package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Select("SELECT * FROM NOTES")
    List<Note> getNotes();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteByNoteid(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNoteByUserid(Integer userid);

    @Update("UPDATE NOTES SET " +
            "notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "WHERE noteid = #{noteid}")
    int updateNoteByNoteid(Note note);

    @Delete("DELETE NOTES WHERE noteid = #{noteid}")
    int deleteNoteByNoteid(Integer noteid);

    @Delete("DELETE NOTES WHERE userid = #{userid}")
    int deleteNoteByUserid(Integer userid);
}
