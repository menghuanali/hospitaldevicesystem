import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.enums.HospitalEnums;
import cn.pch.hospitaldevicesystem.repository.HospitalRepository;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: hospitaldevicesystem
 * @description: 医院测试，添加多家医院
 * @author: 潘成花
 * @create: 2021-01-29 12:34
 **/
@Slf4j
public class HospitalTest extends TmallApplicationTests{
    @Resource
    HospitalRepository hospitalRepository;

    @Test
    public void saveHospitalTest(){
        for(long i=1;i<=5;i++){
            HospitalEnums hospitalEnums = HospitalEnums.of(i);
            Hospital hospital = new Hospital();
            hospital.setId(hospitalEnums.getId());
            hospital.setName(hospitalEnums.getName());
            hospital.setAddress("XX省XX市XX地区XX");
            hospital.setPictureUrl("static/images/hospital/"+hospitalEnums.getId()+".jpg");
            hospital.setTel("9999120");
            hospital.setCreateName("系统");
            hospital.setCreateTime(MyDateUtils.GetNowDate());
            hospitalRepository.save(hospital);
        }
    }
}
