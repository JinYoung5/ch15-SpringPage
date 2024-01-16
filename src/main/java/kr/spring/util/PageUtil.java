package kr.spring.util;

public class PageUtil {
	private int startRow;	 // 한 페이지에서 보여줄 게시글의 시작 번호
	private int endRow;	 // 한 페이지에서 보여줄 게시글의 끝 번호
	private StringBuffer page;// 페이지 표시 문자열

	/**
	 * currentPage : 현재페이지
	 * count : 전체 게시물 수
	 * rowCount : 한 페이지의  게시물의 수
	 * pageCount : 한 화면에 보여줄 페이지 수
	 * pageUrl : 호출 페이지 url
	 * addKey : 부가적인 key 없을 때는 null 처리 (&num=23형식으로 전달할 것)
	 * */
	public PageUtil(int currentPage,int count, int rowCount) {
		//ajax 작업을 할 때 페이지 번호가 보여지는 것이 아니라 다음글 보기 버튼을 누르면 다음 페이지가 보여지는 형식의 작업을
		//할 때 목록 데이터를 호출하기 위해 사용(startRow,endRow 를 구하기 위한 용도로만 사용)
		this(null,null,currentPage,count,rowCount,0,null,null);
	}
	public PageUtil(int currentPage, int count, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,count,rowCount,pageCount,pageUrl,null);
	}
	public PageUtil(int currentPage, int count, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,count,rowCount,pageCount,pageUrl,addKey);
	}
	public PageUtil(String keyfield, String keyword, int currentPage, int count, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,count,rowCount,pageCount,pageUrl,null);
	}
	public PageUtil(String keyfield, String keyword, int currentPage, int count, int rowCount,
			int pageCount,String pageUrl,String addKey) {

		if(count >= 0) {
			String sub_url = "";
			if(keyword != null) sub_url = "&keyfield="+keyfield+"&keyword="+keyword;
			if(addKey != null) sub_url += addKey;

			// 전체 페이지 수
			int totalPage = (int) Math.ceil((double) count / rowCount);
			if (totalPage == 0) {
				totalPage = 1;
			}
			// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
			if (currentPage > totalPage) {
				currentPage = totalPage;
			}
			// 현재 페이지의 처음과 마지막 글의 번호 가져오기.
			startRow = (currentPage - 1) * rowCount + 1;
			endRow = currentPage * rowCount;
			
			// 이전 block 페이지
			page = new StringBuffer();
			if(pageCount > 0) {
				// 시작 페이지와 마지막 페이지 값 구하기.
				int startPage = (int) ((currentPage - 1) / pageCount) * pageCount + 1;
				int endPage = startPage + pageCount - 1;
				// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
				if (endPage > totalPage) {
					endPage = totalPage;
				}
				
				if (currentPage > pageCount) {
					page.append("<a href="+pageUrl+"?pageNum="+ (startPage - 1) + sub_url +">");
					page.append("[이전]");
					page.append("</a>");
				}
				//페이지 번호.현재 페이지는 빨간색으로 강조하고 링크를 제거.
				for (int i = startPage; i <= endPage; i++) {
					if (i > totalPage) {
						break;
					}
					if (i == currentPage) {
						page.append("&nbsp;<b><span style='color:red;'>");
						page.append(i);
						page.append("</span></b>");
					} else {
						page.append("&nbsp;<a href='"+pageUrl+"?pageNum=");
						page.append(i);
						page.append(sub_url+"'>");
						page.append(i);
						page.append("</a>");
					}
					page.append("&nbsp;");
				}
				// 다음 block 페이지
				if (totalPage - startPage >= pageCount) {
					page.append("<a href="+pageUrl+"?pageNum="+ (endPage + 1) + sub_url +">");
					page.append("[다음]");
					page.append("</a>");
				}
			}else {
				page.append("<b>[warning]</b>pageCount는 1이상 지정해야 페이지수가 표시됩니다.");
			}
		}
	}
	public StringBuffer getPage() {
		return page;
	}
	public int getStartRow() {
		return startRow;
	}
	public int getEndRow() {
		return endRow;
	}
}
