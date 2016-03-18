 'use strict';

angular.module('zetravelcloudApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-zetravelcloudApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-zetravelcloudApp-params')});
                }
                return response;
            }
        };
    });
