package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.guliedu.entity.EduSubject;
import com.guli.edu.guliedu.entity.subjectdto.OneSubjectDto;
import com.guli.edu.guliedu.entity.subjectdto.TwoSubjectDto;
import com.guli.edu.guliedu.handler.EduException;
import com.guli.edu.guliedu.mapper.EduSubjectMapper;
import com.guli.edu.guliedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //poi读取excel分类数据
    @Override
    public List<String> importDataSubject(MultipartFile file) {
        try {
            //1获取文件输入流
            InputStream in = file.getInputStream();
            //2创建workbook
            Workbook workbook = WorkbookFactory.create(in);
            //3获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //创建List集合，用于存储错误提示信息
            List<String> list = new ArrayList<>();

            //4获取row
            //excel有多少行不确定，循环遍历取行
            //4.1获取excel有多少行
            int lastRowNum = sheet.getLastRowNum();//最后一行的索引值
            //int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();//获取实际的行数
            //4.2 进行循环遍历获取excel行
            //从第二行开始循环遍历，第一行是表头，不需要添加到数据库里面
            for (int i = 1; i <= lastRowNum; i++) {
                //获取每一行
                Row row = sheet.getRow(i);
                //5获取cell
                //因为列是固定的
                //获取第一列cell，代表一级分类
                Cell cellOne = row.getCell(0);

                //判断第一列
                if (cellOne == null) {//列为空
                    String message = "第" + (i + 1) + "行，第1列数据为空";
                    //把提示信息放到list信息
                    list.add(message);
                    continue;
                }

                //获取第二列cell，代表二级分类
                Cell cellTwo = row.getCell(1);

                //判断第二列
                if (cellTwo == null) {
                    String message = "第" + (i + 1) + "行，第2列数据为空";
                    //把提示信息放到list信息
                    list.add(message);
                    continue;
                }

                //6 获取cell里面值
                //根据不同的类型，调用不同方法取值
                //判断值类型
                CellType oneCellType = cellOne.getCellType();
                String oneLevelValue;
                //判断 string、boolean、数字
                switch (oneCellType) {
                    case STRING:
                        oneLevelValue = cellOne.getStringCellValue();
                        break;
                    case NUMERIC:
                        oneLevelValue = String.valueOf(cellOne.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        oneLevelValue = String.valueOf(cellOne.getBooleanCellValue());
                        break;
                    default:
                        oneLevelValue = "";
                }

                //判断第一列的值
                if (StringUtils.isEmpty(oneLevelValue)) {
                    String message = "第" + (i + 1) + "行，第1列数据为空";
                    //把提示信息放到list信息
                    list.add(message);
                    continue;
                }

                String twoLevelValuel;
                CellType twoCellType = cellTwo.getCellType();
                switch (twoCellType) {
                    case NUMERIC:
                        twoLevelValuel = String.valueOf(cellTwo.getNumericCellValue());
                        break;
                    case STRING:
                        twoLevelValuel = cellTwo.getStringCellValue();
                        break;
                    case BOOLEAN:
                        twoLevelValuel = String.valueOf(cellTwo.getBooleanCellValue());
                        break;
                    default:
                        twoLevelValuel = "";
                }
                //判断第二列的值
                if (StringUtils.isEmpty(twoLevelValuel)) {
                    String message = "第" + (i + 1) + "行，第2列数据为空";
                    //把提示信息放到list信息
                    list.add(message);
                    continue;
                }

                //7 把cell值添加到数据库
                //添加一级分类时候，判断数据库是否存在相同名称的一级分类
                //如果表有相同名称一级分类，不进行添加。如果没有相同的，才进行添加
                //查询数据库进行判断
                EduSubject oneSubjectExists = this.existOneLevelName(oneLevelValue);

                //因为后面添加二级分类的时候，需要一级分类id值
                String oneSubjectLevelId = null;
                if (oneSubjectExists == null) {//数据库没有相同一级分类
                    //把从excel获取一级分类名称添加到数据库里面
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(oneLevelValue);
                    eduSubject.setParentId("0");
                    int insert = baseMapper.insert(eduSubject);
                    // 获取一级分类id
                    oneSubjectLevelId = eduSubject.getId();
                } else {//数据库存在相同的级分类
                    //不进行添加
                    //直接获取一级分类id
                    oneSubjectLevelId = oneSubjectExists.getId();
                }
                //判断一级分类里面是否有相同的二级分类
                EduSubject twoSubjectExists = this.existTwoLevelName(twoLevelValuel, oneSubjectLevelId);
                if (twoSubjectExists == null) {
                    //添加二级分类
                    EduSubject subjectTwo = new EduSubject();
                    subjectTwo.setTitle(twoLevelValuel);
                    subjectTwo.setParentId(oneSubjectLevelId);
                    baseMapper.insert(subjectTwo);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //判断数据库表中是否有相同名称的一级分类
    private EduSubject existOneLevelName(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    //判断数据库里面一级分类下面是否有相同的二级分类
    //两个参数：第一个参数二级分类名称，第二个参数一级分类id
    private EduSubject existTwoLevelName(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }


    //返回所有的分类信息，满足要求的结构
    @Override
    public List<OneSubjectDto> findAllSubjcet() {
        //1 查询所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneLevelSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoLevelSubjectList = baseMapper.selectList(wrapperTwo);

        //3 进行数据封装
        //创建集合用于存储最终封装之后的数据
        List<OneSubjectDto> finalList = new ArrayList<>();

        //4 封装一级分类
        //List<EduSubject> oneLevelSubjectList 集合里面数据  复制到finalList
        for (int i = 0; i < oneLevelSubjectList.size(); i++) {
            //得到集合中每个一级分类
            EduSubject eduSubject = oneLevelSubjectList.get(i);
            //eduSubject里面值复制到OneSubjectDto对象里面
            OneSubjectDto oneDto = new OneSubjectDto();
            BeanUtils.copyProperties(eduSubject, oneDto);
            finalList.add(oneDto);
            //遍历所有的二级分类，得到每个二级分类
            //创建集合，用于存储一级分类里面的所有二级分类
            List<TwoSubjectDto> twoDtoList = new ArrayList<>();
            for (int j = 0; j < twoLevelSubjectList.size(); j++) {
                //得到每个二级分类
                EduSubject twoEduSubject = twoLevelSubjectList.get(j);
                //判断一级分类id和二级分类parentid是否一样，如果一样进行封装
                if (eduSubject.getId().equals(twoEduSubject.getParentId())) {
                    //进行封装
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    BeanUtils.copyProperties(twoEduSubject, twoSubjectDto);
                    //放到twoDtoList集合
                    twoDtoList.add(twoSubjectDto);
                }
            }
            //把封装之后二级分类list集合放到每一个一级分类中
            oneDto.setChildren(twoDtoList);
        }
        return finalList;
    }

    //删除分类的方法
    @Override
    public boolean removeByIdSubject(String id) {
        // 1 查询分类id下面是否有子分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        //如果有子分类，不进行删除
        if (count > 0) {
            throw new EduException(20001, "有子分类不能删除");
        } else {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }
    }

    //添加一级分类
    @Override
    public boolean saveOne(EduSubject eduSubject) {
        //判断表是否存在相同名称一级分类
        EduSubject existOneLevelSubject = this.existOneLevelName(eduSubject.getTitle());
        if (existOneLevelSubject == null) {
            int insert = baseMapper.insert(eduSubject);
            return insert > 0;
        }
        return false;
    }

    //添加二级分类
    @Override
    public boolean saveTwo(EduSubject eduSubject) {
        EduSubject existTwoLevelSubject = this.existTwoLevelName(eduSubject.getTitle(), eduSubject.getParentId());
        if (existTwoLevelSubject == null) {
            int insert = baseMapper.insert(eduSubject);
            return insert > 0;
        }
        return false;
    }
}
