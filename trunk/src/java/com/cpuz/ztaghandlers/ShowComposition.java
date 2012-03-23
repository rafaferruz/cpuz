/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.ztaghandlers;

import com.cpuz.domain.ComponentType;
import com.cpuz.domain.HeaderType;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ShowComposition implements Tag {

	private static final String[] attributesList = {"colspan", "height", "bars", "charsIntroduction"};
	private PageContext pageContext;
	private Tag parent;
	private String dirApplication;
	private String dirHomeResources;
	private Integer columns;
	private Integer lengthIntroduction;
	private String mode;
	private String classPrefix;
	private int ncol;
	private Integer onWidthImage;
	private List<NewsComposition> listComp;
	private Integer indexItemsComp;
	private String image_data_position = "";
	private String image_data_repositoryReference = "";
	private String image_data_userReference = "";
	private Integer image_data_imageHigh = 0;
	private Integer image_data_imageWidth = 0;
	private String image_data_linkedElement = "";
	private boolean isAbstract = false;
	private Integer startAttrNews = -1;
	private Integer endAttrNews = -1;
	private Integer colspan = 1;
	private Integer widthCell = 0;
	private Integer heightCell = 0;
	private boolean showBars = false;
	private Integer charsIntroduction = 0;
	private String attributesNews = "";
	private String valueAttrNews = "";

	public ShowComposition() {
		super();
	}

	@Override
	public void setPageContext(PageContext pc) {
		this.pageContext = pc;
	}

	@Override
	public void setParent(Tag parent) {
		this.parent = parent;
	}

	@Override
	public Tag getParent() {
		return parent;
	}

	@Override
	public int doStartTag() throws JspException {
		return Tag.SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
// Inicialización de variables privadas
		image_data_position = "";
		image_data_repositoryReference = "";
		image_data_userReference = "";
		image_data_imageHigh = 0;
		image_data_imageWidth = 0;
		image_data_linkedElement = "";
		isAbstract = false;
		startAttrNews = -1;
		endAttrNews = -1;
		colspan = 1;
		widthCell = 0;
		heightCell = 0;
		showBars = false;
		charsIntroduction = 0;
		attributesNews = "";
		valueAttrNews = "";


		ShowNews itemsTag = (ShowNews) getParent();
		NewsPiece item = itemsTag.getItem();
		ncol = itemsTag.getNcol();
		columns = itemsTag.getColumns();
		lengthIntroduction = itemsTag.getLengthintroduction();
		mode = itemsTag.getMode();
		classPrefix = itemsTag.getClassPrefix();
		dirApplication = (String) this.pageContext.getAttribute("dirApplication", PageContext.APPLICATION_SCOPE);
		dirHomeResources = (String) this.pageContext.getAttribute("dirHomeResources", PageContext.APPLICATION_SCOPE);
		onWidthImage = itemsTag.getOnWidthImage();

		JspWriter out = pageContext.getOut();

		if (mode == null || !mode.equals("alone")) {
			widthCell = 100 / columns;
		} else {
			widthCell = 100;
		}

		colspan = 1;
		if (item.getShowParameters().contains("colspan=")) {
			String parametersShow = item.getShowParameters();
			int colspanStart = parametersShow.indexOf("colspan=");
			int colspanEnd = parametersShow.indexOf(";", colspanStart);
			try {
				colspan = Integer.parseInt(parametersShow.substring(colspanStart + 8, colspanEnd));
			} catch (Exception ex) {
			}
		}
		heightCell = 0;
		showBars = false;
		charsIntroduction = 0;

		/* Guarda el identificador del registro en el Ambito de la página para su
		 * posterior gestión fuera de la Tag
		 * */
		pageContext.setAttribute("ShowItemId", item.getId());
		try {
			// Se calcula el porcentaje de ancho de celda
			out.println("<td colspan=" + colspan + " width=\"" + (widthCell * colspan) + "%\"   class=\"" + classPrefix + "composition\">");
			// se muestra la fecha de la noticia
			Date rFecha = item.getDatetime();
			String rFechatexto = new SimpleDateFormat("dd/MM/yyyy").format(rFecha);
			out.println("<p class=\"" + classPrefix + "compositionDate\"> ");
			out.println(rFechatexto);
			out.println("</p>");

		} catch (IOException ex) {
			throw new JspException(ex);
		}
// se muestra la fecha de la noticia

		for (NewsComposition itemComp : listComp) {
			try {
				listItem(itemComp, out);
			} catch (IOException e) {
				throw new JspException(e);
			}

		}
		try {
			out.println("<hr/>");
		} catch (IOException ex) {
			throw new JspException(ex);
		}

		/*     if ((ncol += colspan) > columns) {
		ncol = 1;
		}
		 * */
		// fin de la noticia
		ncol += colspan;

		itemsTag.setNcol(ncol);

		return Tag.EVAL_PAGE;


	}

	@Override
	public void release() {
		return;
	}

	private void listItem(NewsComposition item, JspWriter out) throws IOException {

		Integer rNpiId = item.getIdNpi();
		ComponentType componentType = ComponentType.parse(item.getComponentType());
		Integer rOrder = item.getOrder();
		String rHeaderAlternate = item.getHeaderAlt();
		String rHeaderStyle = item.getHeaderStyle();
		String rBodyAbstract = item.getBodyAbstract();
		rBodyAbstract = rBodyAbstract.replaceAll("\n", "<br/>");
		Integer rImageWidth = item.getImageWidth();
		Integer rImageHigh = item.getImageHigh();
		String rLinkedElement = item.getLinkedElement();

		String startLink = "";
		// se muestra gestiona el elemento de composición
		if (componentType.equals(ComponentType.INFOBLOCK)) {
			// GESTION DE ELEMENTO INFOBLOCK
			if (rHeaderStyle.equals(HeaderType.HEADLINE.getKey())) {
				out.println(getParagraphLink(startLink, rHeaderStyle, rHeaderAlternate));
				out.print("<p " + getClassComposition("compositionAbstract") + ">");
				if (rBodyAbstract != null && rBodyAbstract.length() > 0) {
					if (image_data_position.equals("")) {
						out.print(getRBodyAbstractSubString(rBodyAbstract)
								+ getModeNullOrAloneLink(rLinkedElement, rNpiId));
					} else {
						if (!image_data_linkedElement.equals("")) {
							startLink = createLink(image_data_linkedElement);
						}
						out.print(showLink(startLink, showImage())
								+ getRBodyAbstractSubString(rBodyAbstract)
								+ getModeNullOrAloneLink(rLinkedElement, rNpiId));
						image_data_position = "";
						image_data_userReference = "";
						image_data_repositoryReference = "";
						image_data_imageHigh = 0;
						image_data_imageWidth = 0;
						image_data_linkedElement = "";
					}
				}
				out.print("</p>");
				isAbstract = true;
			} else if (rHeaderStyle.equals(HeaderType.SUBTITLE.getKey())) {
				out.println(getParagraphLinkOnNewsDisplay(rNpiId, rOrder, rHeaderStyle, rHeaderAlternate));
				if (isAbstract == false) {
					out.print("<p "
							+ getClassComposition("compositionAbstract") + ">"
							+ rBodyAbstract
							+ getModeNullOrAloneLink(rLinkedElement, rNpiId));
					out.print("</p>");
				}
			} else if (rHeaderStyle.equals(HeaderType.REMARKED.getKey())) {
				out.println(getParagraphLinkOnNewsDisplay(rNpiId, rOrder, rHeaderStyle, rHeaderAlternate));
				if (isAbstract == false) {
					out.print(getParagraphAbstract(rBodyAbstract));
				}
			}
		} else if (componentType.equals(ComponentType.IMAGE)) {
			// GESTION DE ELEMENTO IMAGE
			image_data_position = rHeaderStyle;
			image_data_userReference = rHeaderAlternate;
			image_data_repositoryReference = rBodyAbstract;
			image_data_imageHigh = rImageHigh;
			image_data_imageWidth = rImageWidth;
			image_data_linkedElement = rLinkedElement;
		} else if (componentType.equals(ComponentType.DOCUMENT)) {
			// GESTION DE ELEMENTO DOCUMENT
			startLink = createLink(dirHomeResources + "/../CPUZ/documents/" + rBodyAbstract.replaceAll(" ", "%20") + "\" target=\"_blank");
			if (!rHeaderStyle.equals("")) {
				out.println(getParagraphLink(startLink, rHeaderStyle, rHeaderAlternate));
			}
		}
	}

	private String createLink(String link) {
		return createLink(link, null);
	}

	private String createLink(String link, Integer order) {
		String htmlLink = "";
		if (!link.equals("")) {
			int pieceId = 0;
			try {
				pieceId = Integer.parseInt(link);
// Se ha encontrado un valor numérico que es el ID de una NewsPiece
				htmlLink = "<a "+getClassComposition("composition")+" href=\"" + dirApplication
						+ "/pages/NewsDisplay.jsp?getid=" + pieceId
						+ (order != null ? "&amp;getOrder=" + order : "") + "\">";
			} catch (Exception ex) {
// Se trata de un enlace a una página
				htmlLink = "<a "+getClassComposition("composition")+" href=\"" + link + "\">";
			}
		}
		return htmlLink;
	}

	public Integer getIndexItemsComp() {
		return indexItemsComp;
	}

	public void setIndexItemsComp(Integer indexItemsComp) {
		this.indexItemsComp = indexItemsComp;
	}

	public List<NewsComposition> getListComp() {
		return listComp;
	}

	public void setListComp(List<NewsComposition> listComp) {
		this.listComp = listComp;
	}

	private String showImage() {
		return "<img src=\""
				+ dirHomeResources + "/../CPUZ/images/" + image_data_repositoryReference
				+ "\" "
				+ " alt=\"" + dirHomeResources + "/../CPUZ/images/" + image_data_repositoryReference
				+ "\" "
				+ " align=\"" + image_data_position + "\" height=\"90\" width=\"120\" "
				+ getClassComposition("compositionAbstract")+"/>";
	}

	private String endLink(String startLink) {
		return (!startLink.equals("") ? "</a>" : "");
	}

	private String showLink(String startLink, String elementLink) {
		return startLink + elementLink + endLink(startLink);
	}

	private String getFollowLink(String rLinkedElement, String rNpiId) {
		if (!rLinkedElement.equals("")) {
			return showLink(createLink(rLinkedElement), "  (...continúa)");
		} else {
			return showLink(createLink(rNpiId), "  (...continúa)");
		}
	}

	private String getClassComposition(String rHeaderStyle) {
		String classPrefixLocal = "class=\"" + classPrefix;
		switch (rHeaderStyle) {
			case "Titular":
				return classPrefixLocal + "compositionTitular\">";
			case "Subtítulo":
				return (mode != null && mode.equals("alone")
						? classPrefixLocal + "compositionTitular"
						: classPrefixLocal + "compositionSubtitulo")
						+ "\">";
			case "Destacado":
				return (mode != null && mode.equals("alone")
						? classPrefixLocal + "compositionTitular"
						: classPrefixLocal + "compositionDestacado")
						+ "\">";
			case "compositionAbstract":
				return classPrefixLocal + "compositionAbstract\"";
			case "composition":
				return classPrefixLocal + "composition\"";
		}
		return "";
	}

	private String getRBodyAbstractSubString(String rBodyAbstract) {
		return rBodyAbstract.substring(0, Math.min(rBodyAbstract.length(), lengthIntroduction));
	}

	private String getParagraphLinkOnNewsDisplay(Integer rNpiId, Integer rOrder, String rHeaderStyle, String rHeaderAlternate) {
		String startLink = createLink(rNpiId.toString(), rOrder);
		startLink = (startLink.contains("NewsDisplay")
				? startLink.replace("NewsDisplay", "ComponentDisplay")
				: startLink);
		return getParagraphLink(startLink, rHeaderStyle, rHeaderAlternate);

	}

	private String getParagraphLink(String startLink, String rHeaderStyle, String rHeaderAlternate) {
		return "<p "
				+ getClassComposition(rHeaderStyle) + ">"
				+ showLink(startLink, rHeaderAlternate)
				+ "</p>";

	}

	private String getParagraphAbstract(String rBodyAbstract) {
		return "<p "
				+ getClassComposition("compositionAbstract") + ">"
				+ rBodyAbstract
				+ "</p>";
	}

	private String getModeNullOrAloneLink(String rLinkedElement, Integer rNpiId) {
		if (mode == null || !mode.equals("alone")) {
			return getFollowLink(rLinkedElement, rNpiId.toString());
		}
		return "";
	}
}
