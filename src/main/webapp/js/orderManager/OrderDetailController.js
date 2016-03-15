var OrderDetailController = Ext.extend(Ext.util.Observable,{
	constructor:function(orderDetailGridPanel){
		var outThis = this;
		orderDetailGridPanel.on("applyRefund",function(record){
			
			var callback = new Object();
			callback.success=function(){
				var selectionModel = orderDetailGridPanel.getSelectionModel();
				var record = selectionModel.getSelected();
				Ext.Ajax.request({
					url:"/manager/api/get_orderdetail_by_order?order_id="+record.get("orderId"),
					success:function(resp){
						var obj = eval("("+resp.responseText+")");
						orderDetailGridPanel.getStore().loadData(obj);
					}
				});
			}
			Ext.Msg.prompt("提示","请输入备注信息",function(btn,text){
				if(btn=="ok"){
					outThis.requestHandle(record.id,"apply_refund",text,"",callback);
				}
			});
			
		});
	},
	requestHandle:function(id,way,remarks,logisticsNo,callback){
		var obj = new Object();
		obj.group_partake_id = id;
		obj.way = way;
		obj.logistics_no = logisticsNo;
		obj.remarks = remarks;
		Ext.Ajax.request({
			url:"/manager/api/order_handle",
			method:"POST",
			params:obj,
			success:function(response){
				callback.success(response);
			},
			failure:function(response){
				callback.failure(response);
			}
		});
	}
});