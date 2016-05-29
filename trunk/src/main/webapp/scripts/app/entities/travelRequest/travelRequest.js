'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('travelRequest', {
                parent: 'entity',
                url: '/travelRequests',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.travelRequest.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelRequest/travelRequests.html',
                        controller: 'TravelRequestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('travelRequest');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('travelRequest.detail', {
                parent: 'entity',
                url: '/travelRequest/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.travelRequest.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-detail.html',
                        controller: 'TravelRequestDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('travelRequest');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TravelRequest', function($stateParams, TravelRequest) {
                        return TravelRequest.get({id : $stateParams.id});
                    }]
                }
            })
            
            .state('travelRequest.edit', {
                parent: 'travelRequest',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-dialog.html',
                        controller: 'TravelRequestDialogController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TravelRequest', function($stateParams, TravelRequest) {
                        return TravelRequest.get({id : $stateParams.id});
                    }]
                }
            })

            .state('travelRequest.new', {
                parent: 'travelRequest',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-dialog.html',
                        controller: 'TravelRequestDialogController'
                    }
                },
			      resolve: {
			      entity: function () {
			          return {
			              title: null,
			              description: null,
			              checkin: null,
			              checkout: null,
			              date: null,
			              fileId: null,
			              dateSentToAccounting: null,
			              status: null,
			              paymentType: null,
			              numOfAdults: null,
			              numOfchildren: null,
			              destination: null,
			              id: null
			          };
			      }
			  }
            })

            
//            .state('travelRequest.new', {
//                parent: 'travelRequest',
//                url: '/new',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                    $uibModal.open({
//                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-dialog.html',
//                        controller: 'TravelRequestDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: function () {
//                                return {
//                                    title: null,
//                                    description: null,
//                                    checkin: null,
//                                    checkout: null,
//                                    date: null,
//                                    fileId: null,
//                                    dateSentToAccounting: null,
//                                    status: null,
//                                    paymentType: null,
//                                    numOfAdults: null,
//                                    numOfchildren: null,
//                                    destination: null,
//                                    id: null
//                                };
//                            }
//                        }
//                    }).result.then(function(result) {
//                        $state.go('travelRequest', null, { reload: true });
//                    }, function() {
//                        $state.go('travelRequest');
//                    })
//                }]
//            })
            
//            .state('travelRequest.edit', {
//                parent: 'travelRequest',
//                url: '/{id}/edit',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                    $uibModal.open({
//                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-dialog.html',
//                        controller: 'TravelRequestDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: ['TravelRequest', function(TravelRequest) {
//                                return TravelRequest.get({id : $stateParams.id});
//                            }]
//                        }
//                    }).result.then(function(result) {
//                        $state.go('travelRequest', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            })
            .state('travelRequest.delete', {
                parent: 'travelRequest',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/travelRequest/travelRequest-delete-dialog.html',
                        controller: 'TravelRequestDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TravelRequest', function(TravelRequest) {
                                return TravelRequest.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('travelRequest', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
