'use strict';

angular.module('zetravelcloudApp')
    .factory('OfferedService', function ($resource, DateUtils) {
        return $resource('api/offeredServices/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.confirmationDate = DateUtils.convertDateTimeFromServer(data.confirmationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
