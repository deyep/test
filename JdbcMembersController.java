package jp.ken.jdbc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.jdbc.dao.MembersDao;
import jp.ken.jdbc.entity.Members;
import jp.ken.jdbc.model.MembersSearchModel;

@Controller
public class JdbcMembersController {
	@Autowired
	private MembersDao membersDao;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String toSearch(Model model) {
		model.addAttribute("membersSearchModel", new MembersSearchModel());
		model.addAttribute("headline", "会員検索");
		return "membersSearch";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchMembers(@ModelAttribute MembersSearchModel membersSearchModel, Model model) {
		// IDと氏名が未入力か判定
		boolean idIsEmpty = membersSearchModel.getId().isEmpty();
		boolean nameIsEmpty = membersSearchModel.getName().isEmpty();

		// IDと氏名ともに未入力の場合
		if (idIsEmpty && nameIsEmpty) {
			//全件検索
			List<Members> membersList = membersDao.getList();
			model.addAttribute("membersList", membersList);

		// IDだけが入力された場合
		} else if (!idIsEmpty && nameIsEmpty) {
			try {
				//ID検索
				Integer id = new Integer(membersSearchModel.getId());
				Members members = membersDao.getMembersById(id);

				if (members == null) {
					model.addAttribute("message", "該当データはありません");
				} else {
					List<Members> membersList = new ArrayList<Members>();
					membersList.add(members);
					model.addAttribute("membersList", membersList);
				}
			} catch (NumberFormatException e) {
				model.addAttribute("message", "IDが不正です");
			}

		// 氏名だけが入力された場合
		} else if (idIsEmpty && !nameIsEmpty) {
			// 氏名検索(あいまい検索)
			List<Members> membersList = membersDao.getListByName(membersSearchModel.getName());

			if (membersList.isEmpty()) {
				model.addAttribute("message", "該当データはありません");
			} else {
				model.addAttribute("membersList", membersList);
			}

		// IDと氏名ともに入力された場合
		} else {
			model.addAttribute("message", "ID または氏名のいずれかを入力して下さい");
		}

		model.addAttribute("headline", "会員検索");
		return "membersSearch";
	}
}
