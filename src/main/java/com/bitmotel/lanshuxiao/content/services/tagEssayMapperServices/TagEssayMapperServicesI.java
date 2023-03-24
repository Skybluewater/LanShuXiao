package com.bitmotel.lanshuxiao.content.services.tagEssayMapperServices;

import java.util.List;

public interface TagEssayMapperServicesI {
    public void mapEssayWithTag(Integer tag_id, Integer passage_id);
    public void unmapEssayWithTag(Integer tag_id, Integer passage_id);
    public List<Integer> getTagIdByPassageId(Integer passage_id);
    public List<Integer> getPassageIdByTagId(Integer tag_id);
    public List<Integer> getPassageIdByTagId(Integer tag_id, Integer offset, Integer limit);
}
