package com.wyc.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GoodGroup;

public interface GoodGroupRepository extends CrudRepository<GoodGroup, String>{
    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt = (select max(createAt) from com.wyc.domain.GoodGroup)")
    public GoodGroup selectLastestGoodGroup();
    
    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt = (select max(g2.createAt) from com.wyc.domain.GoodGroup g2 where g2.groupHead=:groupHead)")
    public GoodGroup selectLastestGoodGroupByGroupHead(@Param("groupHead") String groupHead);

    public Page<GoodGroup> findAllByAdminIdOrderByUpdateAtDesc(String adminId,Pageable pageable);

    public Iterable<GoodGroup> findAllByIsDisused(int isDisused);

	public Page<GoodGroup> findAllByAdminIdAndResultOrderByUpdateAtDesc(String adminId, int status, Pageable pageable);
}
