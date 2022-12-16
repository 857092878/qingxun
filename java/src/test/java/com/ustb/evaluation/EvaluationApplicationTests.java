package com.ustb.evaluation;

import com.ustb.evaluation.mod06.VO.AccommodationVO;
import com.ustb.evaluation.mod06.VO.AcidVO;
import com.ustb.evaluation.mod06.VO.AntiepidemicVO;
import com.ustb.evaluation.mod06.VO.StudentVO;
import com.ustb.evaluation.mod06.mapper.*;
import com.ustb.evaluation.mod06.pojo.Accommodation;
import com.ustb.evaluation.mod06.pojo.Healthstatus;
import com.ustb.evaluation.mod06.service.IHealthstatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class EvaluationApplicationTests {

	@Resource
	private StudentMapper studentMapper;
	@Resource
	private AcidMapper acidMapper;
	@Resource
	private AccommodationMapper accommodationMapper;
	@Resource
	private AntiepidemicMapper antiepidemicMapper;
	@Resource
	private IHealthstatusService healthstatusService;
	@Test
	void searchStudent(){
		List<StudentVO> studentVOS = studentMapper.selectStudent();
		for (StudentVO studentVO : studentVOS) {
			System.out.println(studentVO);
		}
	}
	@Test
	void selectAcid(){
		List<AcidVO> acidVOS = acidMapper.selectAcid();
		for (AcidVO acidVO : acidVOS) {
			System.out.println(acidVO);
		}
	}

	@Test
	void selectAccondation(){
		List<AccommodationVO> accommodationVOS = accommodationMapper.selectAccondation();
		for (AccommodationVO accommodationVO : accommodationVOS) {
			System.out.println(accommodationVO);
		}
	}

	@Test
	void selectAntiepidemic(){
		List<AntiepidemicVO> antiepidemicVOS = antiepidemicMapper.selectAntiepidemic();
		for (AntiepidemicVO antiepidemicVO : antiepidemicVOS) {
			System.out.println(antiepidemicVO);
		}
	}
	@Test
	void healStatusFindAll(){
		List<Healthstatus> list = healthstatusService.list();
		for (Healthstatus healthstatus : list) {
			System.out.println(healthstatus);
		}
	}

}
