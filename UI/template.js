(function () {
    var Message;
    Message = function (arg) {
        this.text = arg.text, this.message_side = arg.message_side;
        this.draw = function (_this) {
            return function () {
                var $message;
                $message = $($('.message_template').clone().html());
                $message.addClass(_this.message_side).find('.text').html(_this.text);
                $('.messages').append($message);
                return setTimeout(function () {
                    return $message.addClass('appeared');
                }, 0);
            };
        }(this);
        return this;
    };
	function sleepFor( sleepDuration ){
		
		var now = new Date().getTime();
		while(new Date().getTime() < now + sleepDuration){ /* do nothing */ } 
	}
    $(function () {
        var getMessageText, message_side, sendMessage;
        message_side = 'left';
        getMessageText = function () {
            var $message_input;
            $message_input = $('.message_input');
            return $message_input.val();			
        };
		checkMessage = function (textContent) {
			var $messages, message, $responseContent;
			if (textContent.trim() === '') {
				return;
			}
			$('.message_input').val('');
			$messages = $('.messages');
			message_side = 'left';
			
			$.ajax
			(
				{
					 url: "http://localhost:9003/WMMKSRespond",
					 data: {sentence:textContent},
					 type:'GET',
					 error: function(xhr)
					{
						alert('Ajax request error');
					},
					success: function(response) 
					{
						$responseContent = response;
						message = new Message({
								text: $responseContent,
								message_side: message_side
							});	
						message.draw();
						return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
					}			
				}
			);
					
        };
        sendMessage = function (text) {
            var $messages, message;
            if (text.trim() === '') {
                return;
            }
            $('.message_input').val('');
            $messages = $('.messages');
            message_side = 'right';
			message = new Message({
				text: text,
				message_side: message_side
			});
            message.draw();
            return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
        };
		firstMessage = function (text) {
            var $messages, message;
            if (text.trim() === '') {
                return;
            }
            $('.message_input').val('');
            $messages = $('.messages');
            message_side = 'left';
			message = new Message({
				text: text,
				message_side: message_side
			});
            message.draw();
            return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
        };
        $('.send_message').click(function (e) {
			$temp_message = getMessageText()
            sendMessage($temp_message);
			checkMessage($temp_message);
        });
        $('.message_input').keyup(function (e) {
            if (e.which === 13) {
                $temp_message = getMessageText()
				sendMessage($temp_message);
				checkMessage($temp_message);
            }
        });
		
		firstMessage("Hello, My name is CtbcBot!");
        /*setTimeout(function () {
            return sendMessage('Hi Sandy! How are you?');
        }, 1000);
        setTimeout(function () {
            return sendMessage('I\'m fine, thank you!');
        }, 2000); */
    });
}.call(this));