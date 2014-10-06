<script>
jQuery(document).ready(function() {
  $('#Captcha_ReloadImage').click(function() {
    $('#Captcha_Image').attr('src', '<%=request.getContextPath()%>/jcaptcha?sid=' + new Date().getTime());
    $('#Captcha_UserFeed').val('');
  });
  $('#Captcha_Wiki').click(function() {
      window.open('http://en.wikipedia.org/wiki/Captcha', '', '');
      return false;
  });
});
</script>

<table cellpadding="5" align="center" width="100px" style="border: 1px solid #AAA; background-color: #EEE;">
  <tr>
    <td width="70%" valign="bottom" align="right">
      <img id="Captcha_Image" style="border: 1px dotted #AAA;" src="<%=request.getContextPath()%>/jcaptcha" />
    </td>
    <td width="20%">
      &nbsp;
    </td>
    <td width="10%" align="right" valign="top">
      <img id="Captcha_ReloadImage" src="<%=request.getContextPath()%>/images/icons/reload.png" width="20px" height="20px" alt="Reload Captcha" title="Reload Captcha" style="cursor: pointer;"/>
      <img id="Captcha_Wiki" src="<%=request.getContextPath()%>/images/icons/question.png" width="20px" height="20px" alt="What is this?" title="What is this?" style="cursor: pointer;"/>
    </td>
  </tr>
  <tr>
    <td align="left" colspan="3" style="font-size: 11px;">
      Prove that you are not a robot.<br/>
      Type the letters in the image into the textbox below.
    </td>
  </tr>
  <tr>
    <td align="right" colspan="3">
      <input type="text" id="Captcha_UserFeed" value="" style="width: 98%;"/>
    </td>
  </tr>
</table>
