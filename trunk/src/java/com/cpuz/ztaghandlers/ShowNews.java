/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.ztaghandlers;

import com.cpuz.domain.NewsPiece;
import com.cpuz.tools.Const;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ShowNews extends BodyTagSupport {

	private List<NewsPiece> listNews;
	private Integer indexItems;
	private Integer ncol;
	private Integer columns = Const.SHOW_NEWS_COLUMNS;
	private Integer lengthintroduction = Const.SHOW_NEWS_LENGTH_INTRODUCTION;
	private Integer onWidthImage = Const.SHOW_NEWS_ON_WIDTH_IMAGE;
	private String mode = "";
	private String classPrefix = "";

	/** 
	 * Creates new instance of tag handler 
	 */
	public ShowNews() {
		super();
	}

	////////////////////////////////////////////////////////////////
	///                                                          ///
	///   User methods.                                          ///
	///                                                          ///
	///   Modify these methods to customize your tag handler.    ///
	///                                                          ///
	////////////////////////////////////////////////////////////////
	/**
	 * Method called from doStartTag().
	 * Fill in this method to perform other operations from doStartTag().
	 */
	private void otherDoStartTagOperations() throws JspException {
		// TODO: code that performs other operations in doStartTag
		//       should be placed here.
		//       It will be called after initializing variables, 
		//       finding the parent, setting IDREFs, etc, and 
		//       before calling theBodyShouldBeEvaluated(). 
		//
		//       For example, to print something out to the JSP, use the following:
		//
		//   try {
		//       JspWriter out = pageContext.getOut();
		//       out.println("something");
		//   } catch (IOException ex) {
		//       // do something
		//   }
		indexItems = 0;
		ncol = 1;


	}

	/**
	 * Method called from doEndTag()
	 * Fill in this method to perform other operations from doEndTag().
	 */
	private void otherDoEndTagOperations() throws JspException {
		// TODO: code that performs other operations in doEndTag
		//       should be placed here.
		//       It will be called after initializing variables,
		//       finding the parent, setting IDREFs, etc, and
		//       before calling shouldEvaluateRestOfPageAfterEndTag().

		JspWriter out = pageContext.getOut();
		try {
			if (ncol > 1) {
				for (; ncol <= columns; ncol++) {
					out.println("<td/>");
				}
				out.println("</tr>");
			}

			out.println("</table>");

		} catch (IOException e) {
			throw new JspException(e);
		}

	}

	/**
	 * Fill in this method to process the body content of the tag.
	 * You only need to do this if the tag's BodyContent property
	 * is set to "JSP" or "tagdependent."
	 * If the tag's bodyContent is set to "empty," then this method
	 * will not be called.
	 */
	private void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
		// TODO: insert code to write html before writing the body content.
		// e.g.:
		//
		// out.println("<strong>" + attribute_1 + "</strong>");
		// out.println("   <blockquote>");

		// write the body content (after processing by the JSP engine) on the output Writer


		bodyContent.writeOut(out);

		// Or else get the body content as a string and process it, e.g.:
		//     String bodyStr = bodyContent.getString();
		//     String result = yourProcessingMethod(bodyStr);
		//     out.println(result);

		// TODO: insert code to write html after writing the body content.
		// e.g.:
		//
		// out.println("   </blockquote>");

		// clear the body content for the next time through.
		bodyContent.clearBody();
	}

	////////////////////////////////////////////////////////////////
	///                                                          ///
	///   Tag Handler interface methods.                         ///
	///                                                          ///
	///   Do not modify these methods; instead, modify the       ///
	///   methods that they call.                                ///
	///                                                          ///
	////////////////////////////////////////////////////////////////
	/**
	 * This method is called when the JSP engine encounters the start tag,
	 * after the attributes are processed.
	 * Scripting variables (if any) have their values set here.
	 * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 */
	@Override
	public int doStartTag() throws JspException {
		otherDoStartTagOperations();

		if (theBodyShouldBeEvaluated()) {
			return EVAL_BODY_BUFFERED;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * This method is called after the JSP engine finished processing the tag.
	 * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 */
	@Override
	public int doEndTag() throws JspException {
		otherDoEndTagOperations();

		if (shouldEvaluateRestOfPageAfterEndTag()) {
			return EVAL_PAGE;
		} else {
			return SKIP_PAGE;
		}
	}

	/**
	 * This method is called after the JSP engine processes the body content of the tag.
	 * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 */
	@Override
	public int doAfterBody() throws JspException {
		try {
			// This code is generated for tags whose bodyContent is "JSP"
			BodyContent bodyCont = getBodyContent();
			JspWriter out = bodyCont.getEnclosingWriter();

			writeTagBodyContent(out, bodyCont);
		} catch (Exception ex) {
			handleBodyContentException(ex);
		}

		if (theBodyShouldBeEvaluatedAgain()) {


			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * Handles exception from processing the body content.
	 */
	private void handleBodyContentException(Exception ex) throws JspException {
		// Since the doAfterBody method is guarded, place exception handing code here.
		throw new JspException("Error in ShowNews tag", ex);
	}

	/**
	 * Fill in this method to determine if the restáof the JSP page
	 * should be generated after this tag is finished.
	 * Called from doEndTag().
	 */
	private boolean shouldEvaluateRestOfPageAfterEndTag() {
		// TODO: code that determines whether the restáof the page
		//       should be evaluated after the tag is processed
		//       should be placed here.
		//       Called from the doEndTag() method.
		//
		return true;
	}

	/**
	 * Fill in this method to determine if the tag body should be evaluated
	 * again after evaluating the body.
	 * Use this method to create an iterating tag.
	 * Called from doAfterBody().
	 */
	private boolean theBodyShouldBeEvaluatedAgain() throws JspException {
		try {
			// TODO: code that determines whether the tag body should be
			//       evaluated again after processing the tag
			//       should be placed here.
			//       You can use this method to create iterating tags.
			//       Called from the doAfterBody() method.
			//
			JspWriter out = pageContext.getOut();
			out.println("</td>");
			if (ncol > columns) {
				out.println("</tr>");
				ncol = 1;
				if (indexItems < listNews.size()) {
					out.println("<tr>");
				}
			}
			if (indexItems < listNews.size()) {
// Modificación introducida para compatibilizar la etiqueta con COMPOSICIONES
// Si no se lee de la tabla NewsPieces, se guarda un null que en ShowItem es sustituido
				Integer rId = listNews.get(indexItems).getId();
				pageContext.setAttribute("ShowItemId", rId);
			pageContext.setAttribute("newsCompositionList", listNews.get(indexItems).getNewsCompositionList());
// Fin de la modificación

				return true;
			}
			return false;
		} catch (IOException ex) {
			throw new JspException(ex);
		}
	}

	private boolean theBodyShouldBeEvaluated() throws JspException {
		// TODO: code that determines whether the body should be
		//       evaluated should be placed here.
		//       Called from the doStartTag() method.

		if (!listNews.isEmpty()) {
// Modificación introducida para compatibilizar la etiqueta con COMPOSICIONES  
// Si no se lee de la tabla NewsPieces, se guarda un null que en ShowItem es sustituido
			Integer rId = listNews.get(indexItems).getId();
			pageContext.setAttribute("ShowItemId", rId);
			pageContext.setAttribute("newsCompositionList", listNews.get(indexItems).getNewsCompositionList());
// Fin de la modificación
			JspWriter out = pageContext.getOut();
			try {
				out.println("<table class=\"" + classPrefix + "composition\">");

				if ((ncol % columns) == 1 || columns == 1) {
// se crea una nueva fila si el registro mostrado es impar en el orden a listar
					out.println("<tr class=\"" + classPrefix + "composition\">");
				}
			} catch (IOException e) {
				throw new JspException(e);
			}
			return true;
		}
		return false;
	}

	public void setListNews(List<NewsPiece> listNews) {
		this.listNews = listNews;
	}

	public List<NewsPiece> getListNews() {
		return listNews;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public Integer getColumns() {
		return columns;
	}

	public Integer getIndexItems() {
		return indexItems;
	}

	public void setIndexItems(Integer indexItems) {
		this.indexItems = indexItems;
	}

	public Integer getLengthintroduction() {
		return lengthintroduction;
	}

	public void setLengthintroduction(Integer lengthintroduction) {
		this.lengthintroduction = lengthintroduction;
	}

	public Integer getOnWidthImage() {
		return onWidthImage;
	}

	public void setOnWidthImage(Integer onWidthImage) {
		this.onWidthImage = onWidthImage;
	}

	public NewsPiece getItem() {
		NewsPiece item = listNews.get(indexItems);
		indexItems++;
		return item;
	}

	public Integer getNcol() {
		return ncol;
	}

	public void setNcol(Integer ncol) {
		this.ncol = ncol;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getClassPrefix() {
		return classPrefix;
	}

	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}
}
