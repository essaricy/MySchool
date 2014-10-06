<script type="text/javascript" language="javascript">
    $("document").ready(function() {
        $("#IconToolTip").jContent({
            orientation: 'vertical',
            easing: "easeOutCirc",
            duration: 1000,
            auto: true,
            pause_on_hover: true,
            direction: 'next',
            pause: 1500,
            width: 180,
            height: 90
        });
});
</script>
<div id="IconToolTip" class="jContent">
    <div class="slides">
        <div class="slide">
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/add.jpg" alt="" title="" />
        To add a new record.
            </p>
    </div>
    <div>
      <p class="txt">
        <img src="<%=request.getContextPath()%>/images/icons/update.jpg" alt="" title="" />
        To update an existing record.
          </p>
    </div>
        <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/delete.png" alt="" title="" />
        To delete an existing record.
            </p>
    </div>
    <div>
            <p class="txt">
        <img src="<%=request.getContextPath()%>/images/icons/msexcel.png" alt="" title="" />
        To export table data to Microsoft Excel format.
            </p>
    </div>
    <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/map.png" alt="" title="" />
        To view a selected location on google map.
            </p>
    </div>
        <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/calendar.gif" alt="" title="" />
        - To launch the calendar to select a particular date.</br>
        - To view the information in calendar view.
            </p>
    </div>
    <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/collapse_all.png" alt="" title="" />
        To hide information associated to a particular record.
            </p>
    </div>
    <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/expand_all.png" alt="" title="" />
        To unhide information associated to a particular record.
            </p>
    </div>
    <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/lock.png" alt="" title="" />
        To lock a particular record. Editing of locked items is not possible.
            </p>
    </div>
    <div>
            <p class="txt">
                <img src="<%=request.getContextPath()%>/images/icons/table.png" alt="" title="" />
        To view the information in tables.
            </p>
    </div>
    </div>
</div>