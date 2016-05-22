'use strict';

angular.module('zetravelcloudApp')
    .factory('OfferedServiceType', function ($resource, DateUtils) {
        return $resource('api/offeredServiceTypes/:id', {}, {
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
