<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
                <title>Registration Complete</title>
                <link type="text/css" rel="stylesheet" href="/demo/styles/common-styles.css" />
                <style>
                    .imageContainer {
                        width:520; 
                        height:921px; 
                        background-image: url('mobile.png');
                    }
                    .image { 
                        position: relative;
                        text-align: center;
                    }
                    #smsTable { 
                        position: absolute; 
                        top: 132px; 
                        left: 52px;
                        width: 210px;

                        text-align: left;
                        color: #444444;
                        font-family: Arial;
                        font-size: 14px;
                    }
                </style>
            </head>
            <body leftmargin="0" topmargin="0">
                <div class="image">
                    <img src="http://localhost:8080/demo/images/background/mobile.png" alt="" width="100%" />
                    <table id="smsTable" border="0" cellpadding="4" cellspacing="0" style="valign: top;">
                        <tr>
                            <td class="value" style="valign: top;">
                                <xsl:value-of select="Content" />
                            </td>
                        </tr>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>