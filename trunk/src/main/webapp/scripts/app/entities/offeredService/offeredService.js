'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('offeredService', {
                parent: 'entity',
                url: '/offeredServices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.offeredService.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offeredService/offeredServices.html',
                        controller: 'OfferedServiceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offeredService');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('offeredService.detail', {
                parent: 'entity',
                url: '/offeredService/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.offeredService.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offeredService/offeredService-detail.html',
                        controller: 'OfferedServiceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offeredService');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OfferedService', function($stateParams, OfferedService) {
                        return OfferedService.get({id : $stateParams.id});
                    }]
                }
            })
            .state('offeredService.new', {
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredService/offeredService-dialog.html',
                        controller: 'OfferedServiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sellingPrice: null,
                                    cost: null,
                                    detailsId: null,
                                    confirmationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('offeredService', null, { reload: true });
                    }, function() {
                        $state.go('offeredService');
                    })
                }]
            })
            .state('offeredService.edit', {
                parent: 'offeredService',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredService/offeredService-dialog.html',
                        controller: 'OfferedServiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OfferedService', function(OfferedService) {
                                return OfferedService.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('offeredService', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('offeredService.delete', {
                parent: 'offeredService',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredService/offeredService-delete-dialog.html',
                        controller: 'OfferedServiceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OfferedService', function(OfferedService) {
                                return OfferedService.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('offeredService', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
