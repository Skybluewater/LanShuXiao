package com.bitmotel.lanshuxiao.content.services.tagEssayMapperServices;

import com.bitmotel.lanshuxiao.content.mapper.TagEssayMapper;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class TagEssayMapperServicesImpl implements TagEssayMapperServicesI {
    @Autowired
    TagEssayMapper tagEssayMapper;

    @Override
    public void mapEssayWithTag(Integer tag_id, Integer passage_id) {
        try {
            tagEssayMapper.insertTagEssayMapper(tag_id, passage_id);
        } catch (Exception e) {
            throw new BusinessException("Create many to many mapper failed");
        }
    }

    @Override
    public void unmapEssayWithTag(Integer tag_id, Integer passage_id) {
        try {
            tagEssayMapper.deleteTagEssayMapper(tag_id, passage_id);
        } catch (Exception e) {
            throw new BusinessException("Unmap many to many mapper failed");
        }
    }

    @Override
    public List<Integer> getTagIdByPassageId(Integer passage_id) {
        try {
            return tagEssayMapper.getTagIdByPassageId(passage_id);
        } catch (Exception e) {
            throw new BusinessException("Get tag_id by passage_id failed");
        }
    }

    @Override
    public List<Integer> getPassageIdByTagId(Integer tag_id) {
        try {
            return tagEssayMapper.getPassageIdByTagId(tag_id);
        } catch (Exception e) {
            throw new BusinessException("Get passage_id by tag_id failed");
        }
    }
}
