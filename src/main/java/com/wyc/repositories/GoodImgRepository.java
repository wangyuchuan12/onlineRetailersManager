package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GoodImg;

public interface GoodImgRepository extends CrudRepository<GoodImg, String>{

    public Iterable<GoodImg> findAllByGoodIdOrderByLevelDesc(String goodId);
    
    @Query("select max(level) from com.wyc.domain.GoodImg g where g.goodId = :goodId")
	public int selectMaxLevelByGoodId(@Param("goodId")String goodId);

}
