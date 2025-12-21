package org.example.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.buy;

import java.util.List;
@Mapper
public interface BuyMapper {
   public List<buy> b (int id);
}
