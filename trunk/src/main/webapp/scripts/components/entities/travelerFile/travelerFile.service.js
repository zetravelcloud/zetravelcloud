'use strict';

angular.module('zetravelcloudApp')
    .factory('TravelerFile', function ($resource, DateUtils) {
        return $resource('api/travelerFiles/:id', {}, {
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
