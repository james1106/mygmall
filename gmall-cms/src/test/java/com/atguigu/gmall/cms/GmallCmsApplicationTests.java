package com.atguigu.gmall.cms;

import com.atguigu.gmall.cms.entity.Subject;
import com.atguigu.gmall.cms.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallCmsApplicationTests {

	@Autowired
	SubjectService subjectService;

	@Test
	public void contextLoads() {
//		Subject byId = subjectService.getById(1);
//		System.out.println(byId.getTitle());

//		Subject subject = new Subject();
//		subject.setTitle("9527");
//		boolean save = subjectService.save(subject);

		Subject byId = subjectService.getById(17);
		System.out.println(byId.getTitle());

	}

}
