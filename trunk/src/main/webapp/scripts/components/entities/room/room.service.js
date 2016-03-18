'use strict';

angular.module('zetravelcloudApp')
    .factory('Room', function ($resource, DateUtils) {
        return $resource('api/rooms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
