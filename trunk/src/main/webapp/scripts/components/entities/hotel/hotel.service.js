'use strict';

angular.module('zetravelcloudApp')
    .factory('Hotel', function ($resource, DateUtils) {
        return $resource('api/hotels/:id', {}, {
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


angular.module('zetravelcloudApp')
	.service('ProposalService', function () {
		var proposal = {};
		proposal.proposedRooms = [];
		this.addHotelToProposal = function (hotel) {
//			TODO proposal.city
//			TODO proposal.checkin and checkout
			
			for(var x in hotel.rooms){
				var room = hotel.rooms[x];
				var proposedRoom = {};
				proposedRoom.hotelName = hotel.name;
				proposedRoom.type = room.type;
				proposedRoom.price = room.price
				proposedRoom.currency = room.currency;
				proposal.proposedRooms.push(proposedRoom);
			}
		}
		this.proposalIsEmpty = function() {
			return proposal.proposedRooms.length < 1;
		}
        this.nbOfHotelsInProposal = function(){
        	return proposal.proposedRooms.length;
        }

	});

