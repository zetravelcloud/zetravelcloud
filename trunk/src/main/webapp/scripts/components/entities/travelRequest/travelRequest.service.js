'use strict';

angular.module('zetravelcloudApp')
    .factory('TravelRequest', function ($resource, DateUtils) {
        return $resource('api/travelRequests/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.checkin = DateUtils.convertLocaleDateFromServer(data.checkin);
                    data.checkout = DateUtils.convertLocaleDateFromServer(data.checkout);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.checkin = DateUtils.convertLocaleDateToServer(data.checkin);
                    data.checkout = DateUtils.convertLocaleDateToServer(data.checkout);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.checkin = DateUtils.convertLocaleDateToServer(data.checkin);
                    data.checkout = DateUtils.convertLocaleDateToServer(data.checkout);
                    return angular.toJson(data);
                }
            }
        });
    });
