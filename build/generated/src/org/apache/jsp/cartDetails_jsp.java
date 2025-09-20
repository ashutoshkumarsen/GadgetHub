package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class cartDetails_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!doctype html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("    <title>Bootstrap demo</title>\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css\" integrity=\"sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />\n");
      out.write("</head>\n");
      out.write("  <body>\n");
      out.write("      \n");
      out.write("      ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("      \n");
      out.write("    <div class=\"text-center text-primary mt-3 mb-3 h3\">\n");
      out.write("        Cart items\n");
      out.write("    </div>\n");
      out.write("    <div class=\"container-fluid\">\n");
      out.write("        <div class=\"table-responsive\">\n");
      out.write("            <table class=\"table table-hover\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Product image</th>\n");
      out.write("                        <th>Prodcut Name</th>\n");
      out.write("                        <th>Price</th>\n");
      out.write("                        <th>Qty</th>\n");
      out.write("                        <th>Add</th>\n");
      out.write("                        <th>Remove</th>\n");
      out.write("                        <th>Amount</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td><img src=\"mobile.jfif\" alt=\"\" width=\"100px\" height=\"100px\"></td>\n");
      out.write("                        <td>Iphone</td>\n");
      out.write("                        <td>50000</td>\n");
      out.write("                        <td>\n");
      out.write("                            <form action=\"#\">\n");
      out.write("                                <input type=\"number\" min=\"0\">\n");
      out.write("                                <input type=\"hidden\" name=\"\" value=\"\">\n");
      out.write("                            </form>\n");
      out.write("                        </td>\n");
      out.write("                        <td>\n");
      out.write("                            <a href=\"#\"><i class=\"fa-solid fa-plus\"></i></a>\n");
      out.write("                        </td>\n");
      out.write("                        <td>\n");
      out.write("                            <a href=\"#\"><i class=\"fa-solid fa-minus\"></i></a>\n");
      out.write("                        </td>\n");
      out.write("                        <td>\n");
      out.write("                            50000\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td colspan=\"6\" class=\"text-center\">\n");
      out.write("                            Total Amount in Rupees\n");
      out.write("                        </td>\n");
      out.write("                        <td colspan=\"2\">\n");
      out.write("                            50000\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td colspan=\"4\" class=\"text-center\">\n");
      out.write("                            <form action=\"#\">\n");
      out.write("                                <button type=\"submit\" class=\"btn btn-dark\">Cancel</button>\n");
      out.write("                            </form>\n");
      out.write("                        </td>\n");
      out.write("                        <td colspan=\"3\" class=\"text-center\">\n");
      out.write("                            <form action=\"#\">\n");
      out.write("                                <button type=\"submit\" class=\"btn btn-warning\">Pay Now</button>\n");
      out.write("                            </form>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("      \n");
      out.write("      ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
      out.write("      \n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>\n");
      out.write("  </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
